package main.java.service;

import main.java.dbo.RegistrationDbo;

/**
 * Created by mrlz on 01.04.2018.
 */
public interface RegistrationService {

    Long registerUser(RegistrationDbo rdbo);
}
