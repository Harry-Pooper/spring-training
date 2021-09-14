package main.java.controller;

import main.java.dao.CitiesDao;
import main.java.dao.RegistrationDao;
import main.java.dao.OrgContactsDao;
import main.java.dao.OrgDao;
import main.java.dbo.OrgContactsDbo;
import main.java.dbo.OrgDbo;
import main.java.dbo.OrgPlusDbo;
import main.java.service.SecurityService;
import main.java.service.OrgProfileService;
import main.java.service.OrgRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import main.java.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by mrlz on 03.04.2018.
 */
@Controller
public class OrgInfoController {
    private String VIEW_NAME = "register_org";
    private String WELCOMING_PAGE_VIEW_NAME = "main_page";
    private String PROFILE_VIEW_NAME = "profileorg";
    private String PROFILE_EDIT_VIEW_NAME = "profileorg_edit";

    @Autowired
    private SecurityService securityService;

    @Autowired
    private OrgRegistrationService orgRegistrationService;

    @Autowired
    private OrgProfileService orgProfileService;


//СПИСКИ
    @RequestMapping(value = "/registerorg", method = RequestMethod.GET)
    public String registerOrg(Model model){
        model = CommonService.putHeaderInfo(model);
        model.addAttribute("orgType", orgRegistrationService.getOrgTypeList());
        model.addAttribute("countMem", orgRegistrationService.getcountMemList());
        model.addAttribute("userLogin", securityService.getLoggedInUser().getLogin());
        model.addAttribute("orgInfo", new OrgDbo());
        model.addAttribute("countries", CitiesDao.getCountriesList());
        model.addAttribute("cities", CitiesDao.getCitiesList());
        model.addAttribute("selectedCity", 10771L);
        model.addAttribute("scountMem", 13591L);
        model.addAttribute("sorgType", 13141L);
        return VIEW_NAME;
    }


    @RequestMapping(value = "/registerorg", method = RequestMethod.POST)
    public String registerOrg(
            @ModelAttribute("orgInfo") OrgDbo orgDbo,
            @RequestParam(required = false, value = "btnSend") String send,
            @RequestParam(required = false, value = "btnCancel") String cancel,
            @RequestParam(value="file", required = false) MultipartFile file,
            HttpServletRequest request,
            Model model){
        if(send != null){
            try {
                orgDbo.setCityId(Long.valueOf(request.getParameter("cityName")));
                orgDbo.setOrgType(Long.valueOf(request.getParameter("orgType")));
                orgDbo.setcountMem(Long.valueOf(request.getParameter("countMem")));
                orgRegistrationService.fillOrgProfile(orgDbo, SecurityService.getLoggedInUser().getAccountId());
                orgProfileService.uploadImage(file, OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()).getProfileorgId());
                return "redirect:/";
            } catch (Exception ex) {
                RegistrationDao.deleteUser(SecurityService.getLoggedInUser());
                ex.printStackTrace();
                System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
                return "redirect:/";
            }
        } else {
            RegistrationDao.deleteUser(SecurityService.getLoggedInUser());
            securityService.clearContext();
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/profileorg", method = RequestMethod.GET)
    public String orgProfile(
            @RequestParam(value="id", required = true) Long id,
            @RequestParam(value="edit", required = false) String edit,
            Model model, HttpServletRequest request) {
        model = CommonService.putHeaderInfo(model);
        OrgDbo orgInfo = OrgDao.findOrgByProfileId(id);
        OrgDbo loggedIn = new OrgDbo();
        if(SecurityService.getLoggedInUser() != null) {
            loggedIn = OrgDao.findOrgByAccId(SecurityService.getLoggedInUser());
        }
        if(orgInfo != new OrgDbo()) {
            model.addAttribute("orgInfo", orgInfo);
            model.addAttribute("cityName", CitiesDao.getCityById(orgInfo.getCityId()).get(orgInfo.getCityId()));
            model.addAttribute("authenticatedUser", loggedIn);
            return PROFILE_VIEW_NAME;
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/profileorg/edit", method = RequestMethod.GET)
    public String orgProfileEdit(
            @RequestParam(value="id", required = true) Long profileorgID,
            @RequestParam(value="edit", required = false) String status,
            Model model
    ){
        OrgDbo orgInfo = OrgDao.findOrgByProfileId(profileorgID);
        model = CommonService.putHeaderInfo(model);
        if(orgInfo != new OrgDbo() && SecurityService.getLoggedInUser() != null && Objects.equals(OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()).getProfileorgId(), profileorgID)) {
            model.addAttribute("orgInfo", orgInfo);
            model.addAttribute("orgRegistrationService", orgRegistrationService);
            model.addAttribute("countMemList", orgRegistrationService.getcountMemList());
            model.addAttribute("orgType", orgRegistrationService.getOrgTypeList());
            model.addAttribute("cities", CitiesDao.getCitiesList());
            model.addAttribute("countries", CitiesDao.getCountriesList());
            return PROFILE_EDIT_VIEW_NAME;
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/profileorg/edit", method = RequestMethod.POST)
    public String orgProfileEdit(
            @ModelAttribute(value="orgInfo") OrgDbo orgDbo,
            @RequestParam(value="id", required = true) Long profileorgId,
            @RequestParam(required = false, value = "btnSend") String send,
            @RequestParam(required = false, value = "btnCancel") String cancel,
            @RequestParam(value="file", required = false) MultipartFile file,
            MultipartHttpServletRequest request
    ){
        if(send != null) {
            try {
                orgDbo.setCityId(Long.valueOf(request.getParameter("cityName")));
                orgDbo.setOrgType(Long.valueOf(request.getParameter("orgType")));
                orgDbo.setcountMem(Long.valueOf(request.getParameter("countMem")));
                OrgDao.updateOrgProfileInfo(orgDbo, profileorgId);
                orgProfileService.uploadImage(file, profileorgId);
                ModelAndView mav = new ModelAndView();
                mav.setViewName(PROFILE_VIEW_NAME);
                return "redirect:/profileorg?id=" + profileorgId + "&edit=success";
            } catch (Exception ex){
                ex.printStackTrace();
                System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
                return "redirect:/profileorg?id=" + profileorgId;
            }
        } else {
            return "redirect:/profileorg?id=" + profileorgId;
        }

    }

    @RequestMapping(value = "/profileorg/add", method = RequestMethod.GET)
    public String orgProfileAdd(
            @RequestParam(value = "orgId") Long orgId,
            @RequestParam(value = "type") Long type,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        String param = "";
        //1 - transport, 2 - career, 3 - spirit, 4 - salary, 5 - projects
        if(type == 1L){
            param = "transport";
        }
        if(type == 2L){
            param = "career";
        }
        if(type == 3L){
            param = "spirit";
        }
        if(type == 4L){
            param = "salary";
        }
        if(type == 5L){
            param = "projects";
        }
        OrgDao.addConsForOrg(orgId, param);
        return "redirect:/profileorg?id=" + orgId;
    }
}
