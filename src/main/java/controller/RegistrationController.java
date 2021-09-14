package main.java.controller;

import main.java.Dict;
import main.java.dbo.RegistrationDbo;
import main.java.service.CommonService;
import main.java.service.RegistrationServiceImpl;
import main.java.service.RegistrationValidationService;
import main.java.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by mrlz on 31.03.2018.
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private String VIEW_NAME = "registration";
    private String WELCOMING_PAGE_VIEW_NAME = "main_page";
    private String FIRST_TIME_USER_INFO_INPUT = "register_user";
    private RegistrationDbo authorization = new RegistrationDbo();

    @Autowired
    private RegistrationValidationService rvs;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName(VIEW_NAME);
        mav.addObject("rdbo", SecurityService.getLoggedInUser());
        mav.addObject("userType", Dict.USER);
        mav.addObject("orgType", Dict.ORG);
        mav.addObject("authorization", authorization);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registration(
            @ModelAttribute("authorization") RegistrationDbo auth,
            @RequestParam(required = false, value = "btnSend") String send,
            @RequestParam(required = false, value = "btnCancel") String cancel,
            HttpServletRequest httpServletRequest,
            BindingResult bindingResult,
            Model model) {
        if(send != null) {
            rvs.validate(auth, bindingResult);

            if (bindingResult.hasErrors()) {
                for(Object object : bindingResult.getAllErrors()){
                    if(object instanceof FieldError) {
                        FieldError fieldError = (FieldError) object;

                        System.out.println(fieldError.getCode());
                    }

                    if(object instanceof ObjectError) {
                        ObjectError objectError = (ObjectError) object;

                        System.out.println(objectError.getCode());
                    }
                }

                return VIEW_NAME;
            }

            RegistrationServiceImpl rsi = new RegistrationServiceImpl();
            Long accType = rsi.registerUser(auth);

            securityService.setContext(auth.getLogin(), auth.getPassword());
            SecurityService.getLoggedInUser();

            if (Objects.equals(accType, Dict.USER)) {
                return "redirect:/registeruser";
            } else {
                return "redirect:/registerorg";
            }
        } else {
            return "redirect:/";
        }

    }

}
