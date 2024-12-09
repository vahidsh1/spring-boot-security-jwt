package com.bezkoder.springjwt.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        // Determine the status and message based on the type of exception
        if (authException instanceof UsernameNotFoundException) {
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
            errorMessage = "DisabledException";
        } else if (authException instanceof InternalAuthenticationServiceException) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "Invalid username or password";
        } else if (authException instanceof LockedException) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "Invalid username or password";
        } else {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "Unauthorized";
        }

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        logger.error(authException.getMessage(), authException);
        final Map<String, Object> body = new HashMap<>();
//    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("status", status);
        body.put("error", "Unauthorized");
//    body.put("message", authException.getMessage());
        body.put("message", errorMessage);
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}
