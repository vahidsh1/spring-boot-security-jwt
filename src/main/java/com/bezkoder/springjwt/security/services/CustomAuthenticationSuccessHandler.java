package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private AuthenticationSuccessHandler target = new SavedRequestAwareAuthenticationSuccessHandler();
    @Autowired
    private UserRepository userRepository; // Assume you have a UserService

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException {
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username); // Fetch user details


    }


}