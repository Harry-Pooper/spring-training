package main.java.service;

import main.java.dao.RegistrationDao;
import main.java.dbo.RegistrationDbo;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by mrlz on 02.04.2018.
 */

@Component
public class RegistrationValidationService implements Validator {

    private EmailValidationService emailValidationService = new EmailValidationService(true);

    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationDbo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationDbo rdbo = (RegistrationDbo) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "field.is.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.is.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.is.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secretQuestion", "field.is.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secretAnswer", "field.is.empty");


        if(rdbo.getLogin() != null) {
            if (RegistrationDao.getRegistrationInfoByLogin(rdbo.getLogin()).getLogin() != null) {
                errors.rejectValue("login", "register.user.login.already.exist");
            }
        }
        if(rdbo.getLogin().length() < 3){
            errors.rejectValue("login", "register.user.login.too.short");
        }
        //todo - проверить на наличие букв и цифр
        if(rdbo.getPassword().length() < 8){
            errors.rejectValue("password", "register.user.password.too.short");
        }
        if(rdbo.getEmail().length() >= 1) {
            if(!emailValidationService.isValid(rdbo.getEmail())){
                errors.rejectValue("email", "register.user.email.is.not.correct");
            }
            if (RegistrationDao.searchEmail(rdbo.getEmail()) > 0) {
                errors.rejectValue("email", "register.user.email.already.exist");
            }
        }
        if(rdbo.getAccType() == 0L){
            errors.rejectValue("accType", "register.user.acctype.is.not.chosen");
        }
    }
}
