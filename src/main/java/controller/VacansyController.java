package main.java.controller;

import main.java.dao.*;
import main.java.dbo.PaymentDbo;
import main.java.dbo.VacansyDbo;
import main.java.dbo.OrgDbo;
import main.java.service.CommonService;
import main.java.service.VacansyService;
import main.java.service.SecurityService;
import main.java.service.OrgRegistrationService;
import main.java.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ваня on 21.05.2018.
 */
@Controller
public class VacansyController {

    private static final String MY_VACANSYS_VIEW_NAME = "my_vc";
    private static final String CREATE_VACANSY_VIEW_NAME = "vc_create";
    private static final String EDIT_VACANSY_VIEW_NAME = "vc_edit";
    private static final String VC_VIEW_VIEW_NAME = "vc_view";
    private static final String VC_SEARCH_VIEW_NAME = "vc_search";

    @Autowired
    private OrgRegistrationService orgRegistrationService;

    @Autowired
    private VacansyService vacansyService;

    @RequestMapping(value = "/vacansy", method = RequestMethod.GET)
    public String vacansyGetMethod(
            @RequestParam(required = true, value="id") Long profileorgId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        model = CommonService.putHeaderInfo(model);
        if(Objects.equals(profileorgId, OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()).getProfileorgId())){
            List<VacansyDbo> vacansyList = VacansyDao.getOrgVacansy(profileorgId);
            if(vacansyList.isEmpty()){
                model.addAttribute("isEmpty", true);
            } else {
                model.addAttribute("isEmpty", false);
                model.addAttribute("vacansyList", vacansyList);
                model.addAttribute("publishedVacansyCount", vacansyService.countPublished(vacansyList));
                model.addAttribute("notPublishedVacansyCount", vacansyService.countNotPublished(vacansyList));
            }
            return MY_VACANSYS_VIEW_NAME;
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/vacansy", method = RequestMethod.POST)
    public String vacansyPostMethod(
            @RequestParam(required = true, value = "id") Long profileorgId,
            @RequestParam(required = true, value = "vcId") Long vcId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        String res = vacansyService.publishVacansy(profileorgId, vcId);
        if(Objects.equals(res, "success")){
            return "redirect:/vacansy?id=" + profileorgId + "&publish=success";
        }
        else {
            return "redirect:/vacansy?id=" + profileorgId + "&publish=failure";
        }
    }

    @RequestMapping(value = "/vacansy/delete", method = RequestMethod.GET)
    public String deleteVacansy(
            @RequestParam(value = "vcId", required = true) Long vcId,
            @RequestParam(value = "profileorgId", required = true) Long profileorgId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        //TagsDao.deleteTagsForVc(vcId);
        VacansyDao.deleteOrgVc(vcId);
        return "redirect:/vacansy?id=" + profileorgId;
    }

    @RequestMapping(value = "/createVacansy", method = RequestMethod.GET)
    public String createVacansyGetMethod(
            @RequestParam(value = "id", required = true) Long profileorgId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        model = CommonService.putHeaderInfo(model);
        model.addAttribute("orgInfo", OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()));
        model.addAttribute("vcInfo", new VacansyDbo());
        return CREATE_VACANSY_VIEW_NAME;
    }

    @RequestMapping(value = "/createVacansy", method = RequestMethod.POST)
    public String createVacansyPostMethod(
            @ModelAttribute(value = "vcInfo") VacansyDbo vacansyDbo,
            @RequestParam(value = "id", required = true) Long profileorgId,
            @RequestParam(value = "btnSend", required = false) String send,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        if(send != null) {
            vacansyService.createVacansy(profileorgId, vacansyDbo, request, file);
            return "redirect:/vacansy?id=" + profileorgId + "&create=success";
        } else {
            return "redirect:/vacansy?id=" + profileorgId;
        }
    }

    @RequestMapping(value = "/vacansy/edit", method = RequestMethod.GET)
    public String editVacansyGetMethod(
            @RequestParam(value = "vcId", required = true) Long vcId,
            @RequestParam(value = "profileorgId", required = true) Long profileorgId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        VacansyDbo rdbo = VacansyDao.getOrgVacansyById(vcId);
        model = CommonService.putHeaderInfo(model);
        model.addAttribute("orgInfo", OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()));
        model.addAttribute("vcInfo", rdbo);
        model.addAttribute("tags", TagsDao.getVcTags(vcId));
        return EDIT_VACANSY_VIEW_NAME;
    }

    @RequestMapping(value = "/vacansy/edit", method = RequestMethod.POST)
    public String editVacansyPostMethod(
            @ModelAttribute(value = "vcInfo") VacansyDbo vacansyDbo,
            @RequestParam(value = "vcId", required = true) Long vcId,
            @RequestParam(value = "profileorgId", required = true) Long profileorgId,
            @RequestParam(value = "btnSend", required = false) String send,
            @RequestParam(value = "btnCancel", required = false) String cancel,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        if(send != null) {
            vacansyService.updateVacansy(profileorgId, vacansyDbo, request, file);
            return "redirect:/vacansy?id=" + profileorgId + "&create=success";
        } else {
            return "redirect:/vacansy?id=" + profileorgId;
        }
    }


    @RequestMapping(value = "/vacansy/view", method = RequestMethod.GET)
    public String viewVacansyGetMethod(
            @RequestParam(value = "vcId") Long vcId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        VacansyDbo rdbo = VacansyDao.getOrgVacansyById(vcId);
        OrgDbo udbo = OrgDao.findOrgByProfileId(rdbo.getProfileorgId());
        model = CommonService.putHeaderInfo(model);
        PaymentDbo paymentDbo = new PaymentDbo();
        paymentDbo.setPaymentFrom(rdbo.getSfrom());
        paymentDbo.setPaymentTo(rdbo.getSto());
        paymentDbo.setPaymentType(rdbo.getStype());
        paymentDbo.setPaymentId(0L);
        if(SecurityService.getLoggedInUser() == null || !Objects.equals(udbo.getAccountId(), SecurityService.getLoggedInUser().getAccountId())){
            VacansyDao.incrementWatches(vcId);
        }
        model.addAttribute("paymentInfo", paymentDbo);
        model.addAttribute("vcInfo", rdbo);
        model.addAttribute("orgInfo", udbo);
        model.addAttribute("tags", TagsDao.getVcTags(vcId));
        model.addAttribute("city", CitiesDao.getCityById(udbo.getCityId()).get(udbo.getCityId()));
        return VC_VIEW_VIEW_NAME;
    }


    @RequestMapping(value = "/search/vacansy", method = RequestMethod.GET)
    public String searchGetMethod(
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "orgName", required = false) String orgName,
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "sfrom", required = false) String sfrom,
        @RequestParam(value = "sto", required = false) String sto,
        @RequestParam(value = "tagss", required = false) String tagss,
        Model model,
        HttpServletRequest request,
        HttpServletResponse response
    ){
        model = CommonService.putHeaderInfo(model);
        model.addAttribute("res", vacansyService.searchResults(title, orgName, city, sfrom, sto, tagss));
        model.addAttribute("orgName", orgName);
        model.addAttribute("title", title);
        model.addAttribute("tagss", tagss);

        if(!StringUtil.isNullOrEmpty(sfrom)) {
            model.addAttribute("sfrom", sfrom);
        }
        if(!StringUtil.isNullOrEmpty(sto)) {
            model.addAttribute("sto", sto);
        }

        if(!StringUtil.isNullOrEmpty(city)) {
            model.addAttribute("city", Long.valueOf(city));
        }
        model.addAttribute("citiesList", CitiesDao.getCitiesList());

        return VC_SEARCH_VIEW_NAME;
    }

}
