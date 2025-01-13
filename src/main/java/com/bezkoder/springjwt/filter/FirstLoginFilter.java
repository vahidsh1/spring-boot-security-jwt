package com.bezkoder.springjwt.filter;

import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.handler.HandleErrorResponse;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FirstLoginFilter extends OncePerRequestFilter {
    @Autowired
    HandleErrorResponse handleErrorResponse;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/h2-console")) {
            chain.doFilter(request, response);
            return;
        }
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal(); // Assuming you're using UserDetails
                if (user.isFirstLogin() && !request.getRequestURI().equals("/api/auth/change-password") && !request.getRequestURI().equals("/api/auth/logout")) {
                    logger.error("Cannot set user authentication: ");
                    String message = "First-time users must change their password.";
                    handleErrorResponse.handleErrorResponse(response, HttpStatus.BAD_REQUEST, message, request.getPathInfo());
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            String message = "First-time users must change their password.";
            handleErrorResponse.handleErrorResponse(response, HttpStatus.BAD_REQUEST, message, request.getPathInfo());
            return;

        }

    }
}