package com.ishujaa.blogsapi.security;

import com.ishujaa.blogsapi.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public User getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new IllegalStateException("No authorized user found.");
        }

        return (User) authentication.getPrincipal();
    }

}
