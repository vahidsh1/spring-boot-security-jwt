package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException
    {
        // Check if it's the user's first login (e.g., using a flag in the user's profile)
        if (isFirstLogin(authentication)) {
            response.sendRedirect("/change-password");
        } else {
            // Handle successful login as usual
            // ...
        }
    }

    private boolean isFirstLogin(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal; 

            User user = (User) userDetails; // Assuming User is your custom user entity
            return user.isFirstLogin();
        }
        return false; // Handle cases where the principal is not a UserDetails
    }
}