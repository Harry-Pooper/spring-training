package main.java.controller;

import main.java.Dict;
import main.java.dao.*;
import main.java.dbo.RegistrationDbo;
import main.java.dbo.UserContactsDbo;
import main.java.dbo.UserDbo;
import main.java.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Position;
import java.util.*;

/**
 * Created by mrlz on 03.04.2018.
 */
@Controller
public class UserInfoController {
    private String VIEW_NAME = "register_user";
    private String WELCOMING_PAGE_VIEW_NAME = "main_page";
    private String PROFILE_VIEW_NAME = "profile";
    private String PROFILE_EDIT_VIEW_NAME = "profile_edit";

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserRegistrationValidationService userRegistrationValidationService;

    @RequestMapping(value = "/registeruser", method = RequestMethod.GET)
    public String registerUser(Model model){
        model = CommonService.putHeaderInfo(model);
        model.addAttribute("degrees", userRegistrationService.getDegreeList());
        model.addAttribute("userLogin", securityService.getLoggedInUser().getLogin());
        model.addAttribute("businessTrips", userRegistrationService.getBusinessTripsList());
        model.addAttribute("userInfo", new UserDbo());
        model.addAttribute("countries", CitiesDao.getCountriesList());
        model.addAttribute("cities", CitiesDao.getCitiesList());
        model.addAttribute("selectedCity", 10771L);
        model.addAttribute("positions", PositionDao.getPositions());
        return VIEW_NAME;
    }

    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
    public String registerUser(
            @ModelAttribute("userInfo") UserDbo ud,
            @RequestParam(required = false, value = "btnSend") String send,
            @RequestParam(required = false, value = "btnCancel") String cancel,
            @RequestParam(value="file", required = false) MultipartFile file,
            HttpServletRequest request,
            BindingResult bindingResult,
            Model model){
        if(send != null){
            try {
                ud.setCityId(Long.valueOf(request.getParameter("cityName")));
                userRegistrationService.fillUserProfile(ud, SecurityService.getLoggedInUser().getAccountId());
                userProfileService.uploadImage(file, UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId());
                List<UserContactsDbo> userContactsDboList = userProfileService.fillUserContacts(request);
                List<String> userTags = new ArrayList<>();
                if(!request.getParameter("tagList").equals("")){
                    userTags = Arrays.asList(request.getParameter("tagList").split(","));
                }
                userProfileService.uploadTags(userTags, UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId());
                List<String> userPositions = Arrays.asList(request.getParameter("positionList").split(","));
                userProfileService.uploadPositions(userPositions, UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId());
                if (!userContactsDboList.isEmpty()) {
                    UserContactsDao.createUserContacts(userContactsDboList);
                }
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

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String userProfile(
            @RequestParam(value="id", required = true) Long id,
            @RequestParam(value="edit", required = false) String edit,
            Model model, HttpServletRequest request) {
        model = CommonService.putHeaderInfo(model);
        UserDbo userInfo = UserDao.findUserByProfileId(id);
        if(userInfo != new UserDbo()) {
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("cityName", CitiesDao.getCityById(userInfo.getCityId()).get(userInfo.getCityId()));
            model.addAttribute("phones", UserContactsDao.getPhonesForUser(id));
            model.addAttribute("emails", UserContactsDao.getEmailsForUser(id));
            model.addAttribute("faxes", UserContactsDao.getFaxesForUser(id));
            model.addAttribute("authenticatedUser", UserDao.findUserByAccId(SecurityService.getLoggedInUser()));
            model.addAttribute("userRegistrationService", userRegistrationService);
            model.addAttribute("tags", TagsDao.getUserTags(id));
            model.addAttribute("positions", PositionDao.getUserPositions(id));
            return PROFILE_VIEW_NAME;
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
    public String userProfileEdit(
            @RequestParam(value="id", required = true) Long profileID,
            @RequestParam(value="edit", required = false) String status,
            Model model
    ){
        UserDbo userInfo = UserDao.findUserByProfileId(profileID);
        model = CommonService.putHeaderInfo(model);
        if(userInfo != new UserDbo() && SecurityService.getLoggedInUser() != null && Objects.equals(UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId(), profileID)) {
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("phones", UserContactsDao.getPhonesForUser(profileID));
            model.addAttribute("emails", UserContactsDao.getEmailsForUser(profileID));
            model.addAttribute("faxes", UserContactsDao.getFaxesForUser(profileID));
            model.addAttribute("authenticatedUser", UserDao.findUserByAccId(SecurityService.getLoggedInUser()));
            model.addAttribute("userRegistrationService", userRegistrationService);
            model.addAttribute("degrees", userRegistrationService.getDegreeList());
            model.addAttribute("countries", CitiesDao.getCountriesList());
            model.addAttribute("cities", CitiesDao.getCitiesList());
            model.addAttribute("tags", TagsDao.getUserTags(profileID));
            model.addAttribute("selectedPositions", PositionDao.getUserPositions(profileID));
            model.addAttribute("positions", PositionDao.getPositions());
            if(userInfo.getCityId() == 0L){
                model.addAttribute("selectedCity", 10771L);
            } else {
               model.addAttribute("selectedCity", userInfo.getCityId());
            }
            model.addAttribute("businessTrips", userRegistrationService.getBusinessTripsList());
            if(Objects.equals(status, "success")){
                model.addAttribute("editSuccess", "success");
            } else {
                model.addAttribute("editError", status);
            }
            return PROFILE_EDIT_VIEW_NAME;
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
    public String userProfileEdit(
            @ModelAttribute(value="userInfo") UserDbo userDbo,
            @RequestParam(value="id", required = true) Long profileId,
            @RequestParam(required = false, value = "btnSend") String send,
            @RequestParam(required = false, value = "btnCancel") String cancel,
            @RequestParam(value="file", required = false) MultipartFile file,
            MultipartHttpServletRequest request,
            BindingResult bindingResult,
            Model model
    ){
        if(send != null) {
            userRegistrationValidationService.validate(userDbo, bindingResult);
            List<String> userTags = new ArrayList<>();
            if(!request.getParameter("tagList").equals("")) {
                userTags = Arrays.asList(request.getParameter("tagList").split(","));
            }
            List<String> userPositions = Arrays.asList(request.getParameter("positionList").split(","));
            try {
                userProfileService.uploadImage(file, profileId);
                List<UserContactsDbo> userContactsDboList = userProfileService.fillUserContacts(request);
                userProfileService.uploadTags(userTags, profileId);
                userProfileService.uploadPositions(userPositions, profileId);
                userDbo.setCityId(Long.valueOf(request.getParameter("cityName")));
                UserDao.updateUserProfileInfo(userDbo, profileId);
                UserContactsDao.deleteContactsForUser(profileId);
                if (!userContactsDboList.isEmpty()) {
                    UserContactsDao.createUserContacts(userContactsDboList);
                }
                ModelAndView mav = new ModelAndView();
                mav.setViewName(PROFILE_VIEW_NAME);
                return "redirect:/profile?id=" + profileId + "&edit=success";
            } catch (Exception ex){
                ex.printStackTrace();
                System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
                return "redirect:/profile?id=" + profileId + "&edit=" + ex.getMessage();
            }
        } else {
            return "redirect:/profile?id=" + profileId;
        }
    }
}
