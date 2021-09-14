package main.java.service;

import main.java.Dict;
import main.java.dao.RegistrationDao;
import main.java.dao.UserDao;
import main.java.dbo.RegistrationDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by mrlz on 01.04.2018.
 */

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public Long registerUser(RegistrationDbo rdbo){
        RegistrationDao.registerUser(rdbo);
        RegistrationDbo rdbo1 = RegistrationDao.getRegistrationInfoByLogin(rdbo.getLogin());
        if(rdbo.getAccType().equals(Dict.USER)) {
            return Dict.USER;
        } else {
            return Dict.ORG;
        }
    }

}
