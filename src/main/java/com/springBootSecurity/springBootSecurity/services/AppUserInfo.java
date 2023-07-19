package com.springBootSecurity.springBootSecurity.services;

import com.springBootSecurity.springBootSecurity.model.AppUser;
import com.springBootSecurity.springBootSecurity.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AppUserInfo implements UserDetailsService {

    @Autowired
    public  UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Optional<AppUser> appUser = userRepo.findByUsername(username);
        return  appUser.map(AppUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("User Not found"));
    }
}
