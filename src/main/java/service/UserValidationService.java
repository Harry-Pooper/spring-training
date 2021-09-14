package main.java.service;

import main.java.dbo.UserDbo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by mrlz on 05.04.2018.
 */

@Component
public class UserValidationService implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return UserDbo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        //todo - Сделать проверку на правильность ввода мыла
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.is.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secondName", "field.is.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field.is.empty");
    }
}
