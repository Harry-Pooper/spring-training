package main.java.controller;

import main.java.Dict;
import main.java.dao.*;
import main.java.dbo.ResponseDbo;
import main.java.service.CommonService;
import main.java.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Ваня on 06.06.2018.
 */
@Controller
public class ResponseController {

    private static final String RESPONSE_SEND_VIEW_NAME = "send_resume";
    private static final String RESPONSES_VIEW_NAME ="my_responses";
    private static final String ORG_RESPONSES_VIEW_NAME = "org_responses";
    private static final String ORG_RESPONSES_MORE_VIEW_NAME = "org_responses_more";


    @RequestMapping(value = "/response/send", method = RequestMethod.GET)
    public String responseSendGetMethod(
            @RequestParam(value = "vcId") Long vcId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        model.addAttribute("success", request.getParameter("success"));
        model.addAttribute("vcInfo", VacansyDao.getOrgVacansyById(vcId));
        model.addAttribute("cvList", ResumeDao.getPublicResume(UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId()));
        return RESPONSE_SEND_VIEW_NAME;
    }

    @RequestMapping(value = "/response/send", method = RequestMethod.POST)
    public String responseSendPostMethod(
            @RequestParam(value = "vcId") Long vcId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        ResponseDbo responseDbo = new ResponseDbo();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        responseDbo.setCvId(Long.valueOf(request.getParameter("selectedCv")));
        responseDbo.setVcId(vcId);
        responseDbo.setDateSent(dateFormat.format(new Date()));
        responseDbo.setLetter(request.getParameter("letter"));
        responseDbo.setStatus(Dict.SENT);
        if(ResponseDao.createResponse(responseDbo))
            return "redirect:/response/send?vcId=" + vcId + "&success=true";
        else
            return "redirect:/response/send?vcId=" + vcId + "&success=false";
    }

    @RequestMapping(value = "/responses", method = RequestMethod.GET)
    public String responsesGetMehod(
            @RequestParam(value = "id") Long profileId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        model = CommonService.putHeaderInfo(model);
        model.addAttribute("responseList", ResponseDao.getResponsesForUser(profileId));
        return RESPONSES_VIEW_NAME;
    }

    @RequestMapping(value = "/responses/delete", method = RequestMethod.GET)
    public String responsesDeleteGetMethod(
            @RequestParam(value = "vrId") Long vrId,
            @RequestParam(value = "profileId") Long profileId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        ResponseDao.deleteResponseWithVrId(vrId);
        return "redirect:/responses?id=" + profileId;
    }

    @RequestMapping(value="/orgResponses", method = RequestMethod.GET)
    public String orgResponsesGetMethod(
            @RequestParam(value = "id") Long orgId,
            HttpServletResponse response,
            HttpServletRequest request,
            Model model
    ){
        model = CommonService.putHeaderInfo(model);
        model.addAttribute("responseList", ResponseDao.getResponsesForOrg(orgId));
        return ORG_RESPONSES_VIEW_NAME;
    }

    @RequestMapping(value = "/orgResponses/more", method = RequestMethod.GET)
    public String orgResponsesMoreGetMethod(
            @RequestParam(value = "vrId") Long vrId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        model = CommonService.putHeaderInfo(model);
        model.addAttribute("responseInfo", ResponseDao.getResponse(vrId));
        return ORG_RESPONSES_MORE_VIEW_NAME;
    }

    @RequestMapping(value = "/orgResponses/more", method = RequestMethod.POST)
    public String orgResponsesMorePostMethod(
            @RequestParam(value = "vrId") Long vrId,
            @RequestParam(value = "accept", required = false) String accepted,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        if(Objects.equals(accepted, "accept")){
            ResponseDao.changeStatusForResponse(Dict.ACCEPTED, vrId);
        } else {
            ResponseDao.changeStatusForResponse(Dict.REJECT, vrId);
        }
        return "redirect:/orgResponses?id=" + OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()).getProfileorgId().toString();
    }
}
