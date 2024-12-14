package com.bezkoder.springjwt.filter;

import com.bezkoder.springjwt.config.CachedBodyHttpServletRequest;
import com.bezkoder.springjwt.config.CachedBodyHttpServletResponse;
import com.bezkoder.springjwt.entity.UserAudit;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.service.audit.AuditLogServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuditFilter implements Filter {

    @Autowired
    private AuditLogServiceImpl auditLogServiceImpl; // Service to save audit logs

    @Autowired
    private JwtUtils jwtUtils; // Utility for JWT handling

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getRequestURI().startsWith("/h2-console")) {
            chain.doFilter(request, response); // Proceed without auditing
            return;
        }
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(httpRequest);
        // Create a response wrapper to capture the response body
        CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse((HttpServletResponse) response);
        // Access and log the request body
        String requestBody = wrappedRequest.getBody();
        // Deserialize the JSON body into the LoginRequest object
        LoginRequest loginRequest = objectMapper.readValue(requestBody, LoginRequest.class);
        UserAudit userAudit = new UserAudit();

        if (httpRequest.getRequestURI().startsWith("/api/auth/login")) {
            userAudit.setUsername(loginRequest.getUsername());
            userAudit.setActionResult(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()); // Default to true; change if there's an error

        }
        else  {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userAudit.setUsername(authentication.getName());
            userAudit.setActionResult(authentication.isAuthenticated()); // Default to true; change if there's an error

        }
            // Initialize UserAudit object
            userAudit.setActionTimeStamp(String.valueOf(System.currentTimeMillis())); // Current timestamp
            userAudit.setIpAddress(request.getRemoteAddr());
            userAudit.setRequestBody(requestBody);
            userAudit.setApiMethod(httpRequest.getMethod());
            userAudit.setEndpoint(httpRequest.getRequestURI());
            userAudit.setSessionId(httpRequest.getRequestedSessionId());
            userAudit.setUserAgent(httpRequest.getHeader("User-Agent"));
            userAudit.setDeviceInformation("Device Info Placeholder"); // Add logic to capture actual device info if needed

            try {
                // Proceed with the request
                chain.doFilter(wrappedRequest, wrappedResponse);

                // Capture response status and other details
                userAudit.setResponseStatus(wrappedResponse.getStatus()); // Get status from wrapped response
                userAudit.setResponseBody(wrappedResponse.getResponseBody()); // Get the response body from the wrapper

                // Save the audit log
                auditLogServiceImpl.saveLog(userAudit);

            } catch (Exception e) {
                userAudit.setActionResult(false); // Set action result to false on error
                userAudit.setErrorMessage(e.getMessage()); // Capture error message
                throw e; // Rethrow the exception
            }
        }
    }