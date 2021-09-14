package main.java.controller;

import main.java.Dict;
import main.java.dao.VacansyDao;
import main.java.model.LoginForm;
import main.java.service.CommonService;
import main.java.service.LoginValidationService;
import main.java.service.MainPageService;
import main.java.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by mrlz on 27.03.2018.
 */
@Controller
public class MainPageController {
    private static final String VIEW_NAME="main_page";
    private static final String LOGIN_VIEW_NAME="login";

    @Autowired
    private SecurityService securityService;
    @Autowired
    private LoginValidationService loginValidationService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView page(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.setViewName(VIEW_NAME);
        mav.addObject("lastId", MainPageService.getLastId());
        mav.addObject("rdbo", SecurityService.getLoggedInUser());
        mav.addObject("userType", Dict.USER);
        mav.addObject("organType", Dict.ORG);
        mav.addObject("lastVacansyList", VacansyDao.getLastVacansies());
        mav.addObject("title", "JobToad");
        return mav;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model){
        securityService.clearContext();
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model){
        model = CommonService.putHeaderInfo(model);
        model.addAttribute("loginInfo", new LoginForm());
        return LOGIN_VIEW_NAME;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPage(
            @ModelAttribute("loginInfo") LoginForm loginForm,
            BindingResult bindingResult,
            Model model
    ){
        loginValidationService.validate(loginForm, bindingResult);
        if(bindingResult.hasErrors()){
            return LOGIN_VIEW_NAME;
        }

        securityService.setContext(loginForm.getLogin(), loginForm.getPassword());
        return "redirect:/";
    }
}
