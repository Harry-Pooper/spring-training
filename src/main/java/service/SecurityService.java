package main.java.service;

import main.java.dao.RegistrationDao;
import main.java.dbo.RegistrationDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by mrlz on 04.04.2018.
 */
@Service
public class SecurityService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService uds;

    public static RegistrationDbo getLoggedInUser(){
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof UserDetails){
            return RegistrationDao.getRegistrationInfoByLogin(((UserDetails) userDetails).getUsername());
        }

        return null;
    }

    public void setContext(String username, String password) {
        UserDetails ud = uds.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(ud, password, ud.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    public void clearContext(){
        SecurityContextHolder.clearContext();
    }
}
