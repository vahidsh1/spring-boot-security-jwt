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
        } else {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = authException.getMessage();
        }

        response.setStatus(status);
        response.getWriter().write(errorMessage);
    }

}