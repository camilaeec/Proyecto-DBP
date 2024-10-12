package com.api.rest.canvas2.auth.utils;

import com.api.rest.canvas2.Users.domain.Role;
import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.domain.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtils {

    private final UserService userService;

    @Autowired
    public AuthorizationUtils(@Lazy UserService userService) {
        this.userService = userService;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("No authenticated user found.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByEmail(userDetails.getUsername());
    }

    public boolean hasRole(Role role) {
        User user = getCurrentUser();
        return user.getRole().equals(role);
    }

    public boolean isAdmin() {
        return hasRole(Role.ADMIN);
    }

    public boolean isTeacher() {
        return hasRole(Role.TEACHER);
    }

    public boolean isAssistant() {
        return hasRole(Role.ASSISTANT);
    }

    public boolean isStudent() {
        return hasRole(Role.STUDENT);
    }

    public boolean isAdminOrResourceOwner(Long resourceId) {
        User user = getCurrentUser();
        return user.getId().equals(resourceId) || user.getRole().equals(Role.ADMIN);
    }

    public boolean isTeacherOrAdmin() {
        User user = getCurrentUser();
        return user.getRole().equals(Role.TEACHER) || user.getRole().equals(Role.ADMIN);
    }

    public boolean isAssistantOrTeacher() {
        User user = getCurrentUser();
        return user.getRole().equals(Role.ASSISTANT) || user.getRole().equals(Role.TEACHER);
    }

    public String getCurrentUserEmail() {
        User user = getCurrentUser();
        return user.getEmail();
    }
}
