package com.ne.template.utils;

import com.ne.template.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return true;
        } else {
            return false;
        }
    }

    public static UserPrincipal getLoggedInUser() {
        // Retrieve the currently authenticated user from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return (UserPrincipal) authentication.getPrincipal();
        }
        return null;
    }
}
