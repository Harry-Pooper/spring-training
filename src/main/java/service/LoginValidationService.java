package main.java.service;

import main.java.dao.RegistrationDao;
import main.java.model.LoginForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * Created by mrlz on 05.04.2018.
 */

@Component
public class LoginValidationService implements Validator{
    @Override
    public boolean supports(Class<?> aClass) {
        return LoginForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        LoginForm loginForm = (LoginForm) o;
        //todo - Стоит ли добавлять ещё проверки?
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "field.is.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.is.empty");

        if(RegistrationDao.getRegistrationInfoByLogin(loginForm.getLogin()) == null){
            errors.rejectValue("login", "login.user.password.login.not.correct");
        }
        if(!Objects.equals(RegistrationDao.getRegistrationInfoByLogin(loginForm.getLogin()).getPassword(), loginForm.getPassword())){
            errors.rejectValue("password", "login.user.password.login.not.correct");
        }
    }
}
