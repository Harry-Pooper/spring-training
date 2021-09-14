package main.java.controller;

import main.java.dao.*;
import main.java.dbo.ResumeDbo;
import main.java.dbo.UserDbo;
import main.java.service.CommonService;
import main.java.service.ResumeService;
import main.java.service.SecurityService;
import main.java.service.UserRegistrationService;
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
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ваня on 21.05.2018.
 */
@Controller
public class ResumeController {

    private static final String MY_RESUMES_VIEW_NAME = "my_cv";
    private static final String CREATE_RESUME_VIEW_NAME = "cv_create";
    private static final String EDIT_RESUME_VIEW_NAME = "cv_edit";
    private static final String CV_VIEW_VIEW_NAME = "cv_view";
    private static final String CV_SEARCH_VIEW_NAME = "cv_search";

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private ResumeService resumeService;

    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    public String resumeGetMethod(
            @RequestParam(required = true, value="id") Long profileId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        model = CommonService.putHeaderInfo(model);
        if(Objects.equals(profileId, UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId())){
            List<ResumeDbo> resumeList = ResumeDao.getUserResume(profileId);
            if(resumeList.isEmpty()){
                model.addAttribute("isEmpty", true);
            } else {
                model.addAttribute("isEmpty", false);
                model.addAttribute("resumeList", resumeList);
                model.addAttribute("publishedResumeCount", resumeService.countPublished(resumeList));
                model.addAttribute("notPublishedResumeCount", resumeService.countNotPublished(resumeList));
            }
            return MY_RESUMES_VIEW_NAME;
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    public String resumePostMethod(
            @RequestParam(required = true, value = "id") Long profileId,
            @RequestParam(required = true, value = "cvId") Long cvId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        String res = resumeService.publishResume(profileId, cvId);
        if(Objects.equals(res, "success")){
            return "redirect:/resume?id=" + profileId + "&publish=success";
        }
        else {
            return "redirect:/resume?id=" + profileId + "&publish=failure";
        }
    }

    @RequestMapping(value = "/resume/delete", method = RequestMethod.GET)
    public String deleteResume(
            @RequestParam(value = "cvId", required = true) Long cvId,
            @RequestParam(value = "profileId", required = true) Long profileId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        //TagsDao.deleteTagsForCv(cvId);
        ResumeDao.deleteUserCv(cvId);
        return "redirect:/resume?id=" + profileId;
    }

    @RequestMapping(value = "/createResume", method = RequestMethod.GET)
    public String createResumeGetMethod(
            @RequestParam(value = "id", required = true) Long profileId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        model = CommonService.putHeaderInfo(model);
        model.addAttribute("cities", CitiesDao.getCitiesList());
        model.addAttribute("selectedCity", 10771L);
        model.addAttribute("degrees", userRegistrationService.getDegreeList());
        model.addAttribute("businessTrips", userRegistrationService.getBusinessTripsList());
        model.addAttribute("userInfo", UserDao.findUserByAccId(SecurityService.getLoggedInUser()));
        model.addAttribute("cvInfo", new ResumeDbo());
        model.addAttribute("userPositions", PositionDao.getUserPositions(profileId));
        return CREATE_RESUME_VIEW_NAME;
    }

    @RequestMapping(value = "/createResume", method = RequestMethod.POST)
    public String createResumePostMethod(
            @ModelAttribute(value = "cvInfo") ResumeDbo resumeDbo,
            @RequestParam(value = "id", required = true) Long profileId,
            @RequestParam(value = "btnSend", required = false) String send,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        if(send != null) {
            resumeService.createResume(profileId, resumeDbo, request, file);
            return "redirect:/resume?id=" + profileId + "&create=success";
        } else {
            return "redirect:/resume?id=" + profileId;
        }
    }

    @RequestMapping(value = "/resume/edit", method = RequestMethod.GET)
    public String editResumeGetMethod(
            @RequestParam(value = "cvId", required = true) Long cvId,
            @RequestParam(value = "profileId", required = true) Long profileId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        ResumeDbo rdbo = ResumeDao.getUserResumeById(cvId);
        model = CommonService.putHeaderInfo(model);
        model.addAttribute("cities", CitiesDao.getCitiesList());
        model.addAttribute("selectedCity", 10771L);
        model.addAttribute("degrees", userRegistrationService.getDegreeList());
        model.addAttribute("businessTrips", userRegistrationService.getBusinessTripsList());
        model.addAttribute("userInfo", UserDao.findUserByAccId(SecurityService.getLoggedInUser()));
        model.addAttribute("cvInfo", rdbo);
        model.addAttribute("paymentInfo", PaymentDao.getPaymentById(rdbo.getPaymentId()));
        model.addAttribute("tags", TagsDao.getCvTags(cvId));
        model.addAttribute("userPositions", PositionDao.getUserPositions(profileId));
        model.addAttribute("selectedPosition", PositionDao.getPositionForCv(cvId));
        return EDIT_RESUME_VIEW_NAME;
    }

    @RequestMapping(value = "/resume/edit", method = RequestMethod.POST)
    public String editResumePostMethod(
            @ModelAttribute(value = "cvInfo") ResumeDbo resumeDbo,
            @RequestParam(value = "cvId", required = true) Long cvId,
            @RequestParam(value = "profileId", required = true) Long profileId,
            @RequestParam(value = "btnSend", required = false) String send,
            @RequestParam(value = "btnCancel", required = false) String cancel,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        if(send != null) {
            resumeService.updateResume(profileId, resumeDbo, request, file);
            return "redirect:/resume?id=" + profileId + "&create=success";
        } else {
            return "redirect:/resume?id=" + profileId;
        }
    }

    @RequestMapping(value = "/resume/view", method = RequestMethod.GET)
    public String viewResumeGetMethod(
            @RequestParam(value = "cvId") Long cvId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        ResumeDbo rdbo = ResumeDao.getUserResumeById(cvId);
        UserDbo udbo = UserDao.findUserByProfileId(rdbo.getProfileId());
        model = CommonService.putHeaderInfo(model);
        if(SecurityService.getLoggedInUser() == null || !Objects.equals(udbo.getAccountId(), SecurityService.getLoggedInUser().getAccountId())){
            ResumeDao.incrementWatches(cvId);
        }
        model.addAttribute("loggedInUser", SecurityService.getLoggedInUser());
        model.addAttribute("cvInfo", rdbo);
        model.addAttribute("userInfo", udbo);
        model.addAttribute("paymentInfo", PaymentDao.getPaymentById(rdbo.getPaymentId()));
        model.addAttribute("position", PositionDao.getPositionForCv(cvId));
        model.addAttribute("tags", TagsDao.getCvTags(cvId));
        model.addAttribute("degree", userRegistrationService.getDegreeType(udbo.getDegree()));
        model.addAttribute("business_trip", userRegistrationService.getBusinessTripType(udbo.getBusinessTrips()));
        model.addAttribute("city", CitiesDao.getCityById(udbo.getCityId()).get(udbo.getCityId()));
        return CV_VIEW_VIEW_NAME;
    }

    @RequestMapping(value = "/resume/view", method = RequestMethod.POST)
    public String viewResumePostMethod(
            @RequestParam(required = true, value = "cvId") Long cvId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        Long profileId = Long.valueOf(request.getParameter("profileId"));
        String res = resumeService.publishResume(profileId, cvId);
        if(Objects.equals(res, "success")){
            return "redirect:/resume?id=" + profileId + "&publish=success";
        }
        else {
            return "redirect:/resume?id=" + profileId + "&publish=failure";
        }
    }

    @RequestMapping(value = "/search/resume", method = RequestMethod.GET)
    public String searchGetMethod(
        @RequestParam(value = "firstName", required = false) String firstName,
        @RequestParam(value = "secondName", required = false) String secondName,
        @RequestParam(value = "lastName", required = false) String lastName,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "paymentFrom", required = false) String paymentFrom,
        @RequestParam(value = "paymentTo", required = false) String paymentTo,
        @RequestParam(value = "tags", required = false) String tags,
        @RequestParam(value = "position", required = false) String position,
        Model model,
        HttpServletRequest request,
        HttpServletResponse response
    ){
        model = CommonService.putHeaderInfo(model);
        model.addAttribute("res", resumeService.searchResults(firstName, secondName, lastName, title, position, paymentFrom, paymentTo, tags));
        model.addAttribute("firstName", firstName);
        model.addAttribute("secondName", secondName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("title", title);
        model.addAttribute("tags", tags);

        if(!StringUtil.isNullOrEmpty(paymentFrom)) {
            model.addAttribute("paymentFrom", paymentFrom);
        }
        if(!StringUtil.isNullOrEmpty(paymentTo)) {
            model.addAttribute("paymentTo", paymentTo);
        }
        /*
        if (!StringUtil.isNullOrEmpty(tags)) {
            model.addAttribute("tags", Arrays.asList(tags.split(",")));
        }*/
        if(!StringUtil.isNullOrEmpty(position)) {
            model.addAttribute("position", Long.valueOf(position));
        }
        model.addAttribute("positions", PositionDao.getPositions());

        return CV_SEARCH_VIEW_NAME;
    }
}
