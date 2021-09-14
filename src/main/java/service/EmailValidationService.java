package main.java.service;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Created by mrlz on 12.04.2018.
 */
public class EmailValidationService extends EmailValidator {

    protected EmailValidationService(boolean allowLocal) {
        super(allowLocal);
    }
}
