package com.bezkoder.springjwt.filter;
//
//import com.bezkoder.springjwt.config.CachedBodyHttpServletRequest;
//import com.bezkoder.springjwt.entity.UserAuditRequest;
//import com.bezkoder.springjwt.entity.UserAuditResponse;
//import com.bezkoder.springjwt.entity.UserEntity;
//import com.bezkoder.springjwt.payload.request.LoginRequest;
//import com.bezkoder.springjwt.repository.UserAuditRequestRepository;
//import com.bezkoder.springjwt.repository.UserAuditResponseRepository;
//import com.bezkoder.springjwt.repository.UserRepository;
//import com.bezkoder.springjwt.security.services.UserDetailsImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.catalina.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.RequestEntity;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.util.Optional;
//@Component
//public class ApiLoggingFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private UserAuditRequestRepository userAuditRequestRepository;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Autowired
//    private UserAuditResponseRepository userAuditResponseRepository;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        if (request.getRequestURI().startsWith("/h2-console")) {
//            filterChain.doFilter(request, response); // Proceed without auditing
//            return;
//        }
//        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request);
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserEntity authenticatedUser = getAuthenticatedUser(authentication);
//        UserAuditRequest userAuditRequest = new UserAuditRequest(authenticatedUser);
//        UserAuditResponse userAuditResponse = new UserAuditResponse(userAuditRequest);
//        String requestBody = new String(wrappedRequest.getBody());
//
//        if (request.getRequestURI().startsWith("/api/auth/login")) {
//            LoginRequest loginRequest = objectMapper.readValue(requestBody, LoginRequest.class);
//            userAuditRequest.setUser(authenticatedUser);
//            userAuditResponse.setStatus(getResponseMessage(response)); // Default to true; change if there's an error
//
//        } else {
//            //            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            userAuditRequest.setUser(authenticatedUser);
//            userAuditResponse.setStatus(getResponseMessage(response)); // Default to true; change if there's an error
//
//        }
//
//
//        String method = request.getMethod();
//        String path = request.getRequestURI();
//        String requestPayload = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
//        String sessionId = request.getSession().toString();
//        String localAddress = request.getLocalAddr().toString();
//        // Create RequestEntity object
//        // Log request
//        userAuditRequest.setEndpoint(wrappedRequest.getRequestURI());
//        userAuditRequest.setMethod(wrappedRequest.getMethod());
//
//        userAuditRequest.setTimestamp(LocalDateTime.now());
//        userAuditRequest.setRequestBody(new String(wrappedRequest.getBody())); // Save the body
//        userAuditRequestRepository.save(userAuditRequest);
//
//        // Proceed with the filter chain
//        filterChain.doFilter(wrappedRequest, response);
//
//        // Log response
//        userAuditResponse.setUserAuditRequest(userAuditRequest);
//        userAuditResponse.setTimestamp(LocalDateTime.now());
//        userAuditResponse.setStatus(String.valueOf(response.getStatus()));
//        userAuditResponse.setMessage(getResponseMessage(response));
//
//        userAuditResponseRepository.save(userAuditResponse);
//    }
//
//    private String getResponseMessage(HttpServletResponse response) {
//        // You can customize this to include more detailed messages based on status codes
//        switch (response.getStatus()) {
//            case 200:
//                return "Success";
//            case 201:
//                return "Created";
//            case 403:
//                return "Forbidden: User does not have permission to access this resource";
//            case 401:
//                return "Unauthorized: Authentication required";
//            default:
//                return "Response logged with status: " + response.getStatus();
//        }
//
//    }
//
//    public UserEntity getAuthenticatedUser(Authentication authentication) {
//        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
//            Object principal = authentication.getPrincipal();
//
////                return (UserEntity) principal; // Cast to your custom user type
//           return  (UserEntity) authentication.getPrincipal();
//        }
//        return null; // Return null or throw an exception if no user is found
//    }
//}


import com.bezkoder.springjwt.entity.UserAuditRequest;
import com.bezkoder.springjwt.entity.UserAuditResponse;
import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.repository.UserAuditRequestRepository;
import com.bezkoder.springjwt.repository.UserAuditResponseRepository;
import com.bezkoder.springjwt.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.nio.charset.StandardCharsets;

@Component
public class ApiLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingFilter.class);
    private final UserAuditRequestRepository userAuditRequestRepository;
    private final UserAuditResponseRepository userAuditResponseRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public ApiLoggingFilter(UserAuditRequestRepository userAuditRequestRepository,
                            UserAuditResponseRepository userAuditResponseRepository,
                            UserRepository userRepository) {
        this.userAuditRequestRepository = userAuditRequestRepository;
        this.userAuditResponseRepository = userAuditResponseRepository;
        this.userRepository = userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        long startTime = System.currentTimeMillis();
        String username = "ANONYMOUS";
        String jwtToken = extractJwtFromRequest(request);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
            }

            UserAuditRequest auditRequest = saveRequest(wrappedRequest, username);
            saveResponse(wrappedResponse, auditRequest, username, jwtToken);

            logger.info("Request: {} {} - User: {} - Status: {} - Duration: {} ms",
                    wrappedRequest.getMethod(), wrappedRequest.getRequestURI(), username, wrappedResponse.getStatus(), duration);

            wrappedResponse.copyBodyToResponse();  // Ensure response is flushed
        }
    }

    private UserAuditRequest saveRequest(ContentCachingRequestWrapper request, String username) {
        UserAuditRequest userAuditRequest = new UserAuditRequest();
        userAuditRequest.setEndpoint(request.getRequestURI());
        userAuditRequest.setMethod(request.getMethod());
        userAuditRequest.setIpAddress(request.getRemoteAddr());
        userAuditRequest.setTimestamp(LocalDateTime.now());

        // Extract request body
        String requestBody = extractRequestBody(request);
        userAuditRequest.setRequestBody(requestBody);

        if (!username.equals("ANONYMOUS")) {
            UserEntity userEntity = findOrCreateUser(username);
            userAuditRequest.setUser(userEntity);
        }

        userAuditRequestRepository.save(userAuditRequest);
        return userAuditRequest;
    }

    private void saveResponse(ContentCachingResponseWrapper response, UserAuditRequest request, String username, String jwtToken) {
        UserAuditResponse responseLog = new UserAuditResponse();
        responseLog.setRequest(request);
        responseLog.setStatusCode(response.getStatus());
        responseLog.setTimestamp(LocalDateTime.now());
//        responseLog.setJwtToken(jwtToken);

        // Extract response body
        String responseBody = extractResponseBody(response);
        responseLog.setResponseBody(responseBody);

//            responseLog.setReasonCode(response.getStatus());


        if (!username.equals("ANONYMOUS")) {
            UserEntity userEntity = findOrCreateUser(username);
//            responseLog.se(userEntity);
        }

        userAuditResponseRepository.save(responseLog);
    }

    private String extractRequestBody(ContentCachingRequestWrapper request) {
        byte[] contentAsByteArray = request.getContentAsByteArray();
        return (contentAsByteArray.length > 0)
                ? new String(contentAsByteArray, StandardCharsets.UTF_8)
                : "";
    }

    private String extractResponseBody(ContentCachingResponseWrapper response) {
        byte[] contentAsByteArray = response.getContentAsByteArray();
        return (contentAsByteArray.length > 0)
                ? new String(contentAsByteArray, StandardCharsets.UTF_8)
                : "";
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private UserEntity findOrCreateUser(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        return userEntity.orElseGet(() -> {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(username);
            return userRepository.save(newUser);
        });
    }
}
