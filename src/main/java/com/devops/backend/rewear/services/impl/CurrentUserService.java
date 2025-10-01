package com.devops.backend.rewear.services.impl;

import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.exceptions.UserNotAuthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException("no hay un usuario autenticado, inicie sesion");
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof User user) {
            return user;
        }
        throw new UserNotAuthenticatedException("Unexpected principal type");
    }
}
