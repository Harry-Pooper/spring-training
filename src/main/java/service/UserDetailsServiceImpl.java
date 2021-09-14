package main.java.service;

import main.java.Dict;
import main.java.dao.RegistrationDao;
import main.java.dbo.RegistrationDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mrlz on 01.04.2018.
 */

public class UserDetailsServiceImpl implements UserDetailsService {

    private RegistrationDao rdao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        RegistrationDbo rdbo = rdao.getRegistrationInfoByLogin(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(rdbo.getAccType() == Dict.USER){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        if(rdbo.getAccType() == Dict.ORG){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ORG"));
        }

        return new org.springframework.security.core.userdetails.User(rdbo.getLogin(), rdbo.getPassword(), grantedAuthorities);
    }
}
