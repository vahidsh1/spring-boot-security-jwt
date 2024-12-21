//package com.bezkoder.springjwt.filter;
//
//import com.bezkoder.springjwt.config.CachedBodyHttpServletRequest;
//import com.bezkoder.springjwt.config.CachedBodyHttpServletResponse;
//import com.bezkoder.springjwt.entity.UserAuditRequest;
//import com.bezkoder.springjwt.payload.request.LoginRequest;
//import com.bezkoder.springjwt.security.jwt.JwtUtils;
//import com.bezkoder.springjwt.service.audit.AuditLogServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class AuditFilter implements Filter {
//
//    @Autowired
//    private AuditLogServiceImpl auditLogServiceImpl; // Service to save audit logs
//
//    @Autowired
//    private JwtUtils jwtUtils; // Utility for JWT handling
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//
//        if (httpRequest.getRequestURI().startsWith("/h2-console")) {
//            chain.doFilter(request, response); // Proceed without auditing
//            return;
//        }
//        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(httpRequest);
//        // Create a response wrapper to capture the response body
//        CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse((HttpServletResponse) response);
//        // Access and log the request body
//        String requestBody = wrappedRequest.getBody();
//        // Deserialize the JSON body into the LoginRequest object
////        LoginRequest loginRequest = objectMapper.readValue(requestBody, LoginRequest.class);
//        UserAuditRequest userAuditRequest = new UserAuditRequest();
//
//        if (httpRequest.getRequestURI().startsWith("/api/auth/login")) {
//            LoginRequest loginRequest = objectMapper.readValue(requestBody, LoginRequest.class);
//            userAuditRequest.setUsername(loginRequest.getUsername());
//            userAuditRequest.setActionResult(
//                            SecurityContextHolder
//                            .getContext()
//                            .getAuthentication()
//                            .isAuthenticated()
//            ); // Default to true; change if there's an error
//
//        }
//        else  {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            userAuditRequest.setUsername(authentication.getName());
//            userAuditRequest.setActionResult(authentication.isAuthenticated()); // Default to true; change if there's an error
//
//        }
//            // Initialize UserAudit object
//            userAuditRequest.setActionTimeStamp(String.valueOf(System.currentTimeMillis())); // Current timestamp
//            userAuditRequest.setIpAddress(request.getRemoteAddr());
//            userAuditRequest.setRequestBody(requestBody);
//            userAuditRequest.setApiMethod(httpRequest.getMethod());
//            userAuditRequest.setEndpoint(httpRequest.getRequestURI());
//            userAuditRequest.setSessionId(httpRequest.getRequestedSessionId());
//            userAuditRequest.setUserAgent(httpRequest.getHeader("User-Agent"));
//            userAuditRequest.setDeviceInformation("Device Info Placeholder"); // Add logic to capture actual device info if needed
//
//            try {
//                // Proceed with the request
//                chain.doFilter(wrappedRequest, wrappedResponse);
//
//                // Capture response status and other details
//                userAuditRequest.setResponseStatus(wrappedResponse.getStatus()); // Get status from wrapped response
//                userAuditRequest.setResponseBody(wrappedResponse.getResponseBody()); // Get the response body from the wrapper
//
//                // Save the audit log
//                auditLogServiceImpl.saveLog(userAuditRequest);
//
//            } catch (Exception e) {
//                userAuditRequest.setActionResult(false); // Set action result to false on error
//                userAuditRequest.setErrorMessage(e.getMessage()); // Capture error message
//                throw e; // Rethrow the exception
//            }
//        }
//    }