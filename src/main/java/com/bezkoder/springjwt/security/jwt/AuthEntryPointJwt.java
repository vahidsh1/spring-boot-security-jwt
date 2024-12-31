package com.bezkoder.springjwt.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());

        int status;
        String errorMessage;

        // JWT Specific Exceptions
        Exception jwtException = (Exception) request.getAttribute("jwtException");
        if (jwtException != null) {
            if (jwtException instanceof ExpiredJwtException) {
                status = HttpServletResponse.SC_UNAUTHORIZED;
                errorMessage = "JWT token is expired";
            } else if (jwtException instanceof SignatureException) {
                status = HttpServletResponse.SC_UNAUTHORIZED;
                errorMessage = "Invalid JWT signature";
            } else if (jwtException instanceof MalformedJwtException) {
                status = HttpServletResponse.SC_UNAUTHORIZED;
                errorMessage = "Malformed JWT token";
            } else {
                status = HttpServletResponse.SC_UNAUTHORIZED;
                errorMessage = "JWT token validation failed";
            }
        }
        // Authentication Exceptions
        else if (authException instanceof UsernameNotFoundException) {
            status = HttpServletResponse.SC_NOT_FOUND;
            errorMessage = "User not found";
        } else if (authException instanceof BadCredentialsException) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "Invalid username or password";
        } else if (authException instanceof AccountExpiredException) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "Account is expired";
        } else if (authException instanceof AuthenticationCredentialsNotFoundException) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "User not found";
        } else if (authException instanceof CredentialsExpiredException) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "Password is expired";
        } else if (authException instanceof DisabledException) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "Account is disabled";
        } else if (authException instanceof InternalAuthenticationServiceException) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "Invalid username or password";
        } else if (authException instanceof LockedException) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "Account is locked";
        } else {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "Unauthorized";
        }

        response.setStatus(status);
        response.getWriter().write(errorMessage);
    }
}