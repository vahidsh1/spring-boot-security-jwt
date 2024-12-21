package com.bezkoder.springjwt.filter;

import com.bezkoder.springjwt.config.CachedBodyHttpServletRequest;
import com.bezkoder.springjwt.entity.UserAuditRequest;
import com.bezkoder.springjwt.entity.UserAuditResponse;
import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.repository.UserAuditRequestRepository;
import com.bezkoder.springjwt.repository.UserAuditResponseRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
@Component
public class ApiLoggingFilter extends OncePerRequestFilter {

    @Autowired
    private UserAuditRequestRepository userAuditRequestRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserAuditResponseRepository userAuditResponseRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/h2-console")) {
            filterChain.doFilter(request, response); // Proceed without auditing
            return;
        }
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity authenticatedUser = getAuthenticatedUser(authentication);
        UserAuditRequest userAuditRequest = new UserAuditRequest(authenticatedUser);
        UserAuditResponse userAuditResponse = new UserAuditResponse(userAuditRequest);
        String requestBody = new String(wrappedRequest.getBody());

        if (request.getRequestURI().startsWith("/api/auth/login")) {
            LoginRequest loginRequest = objectMapper.readValue(requestBody, LoginRequest.class);
            userAuditRequest.setUser(authenticatedUser);
            userAuditResponse.setStatus(getResponseMessage(response)); // Default to true; change if there's an error

        } else {
            //            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userAuditRequest.setUser(authenticatedUser);
            userAuditResponse.setStatus(getResponseMessage(response)); // Default to true; change if there's an error

        }


        String method = request.getMethod();
        String path = request.getRequestURI();
        String requestPayload = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        String sessionId = request.getSession().toString();
        String localAddress = request.getLocalAddr().toString();
        // Create RequestEntity object
        // Log request
        userAuditRequest.setEndpoint(wrappedRequest.getRequestURI());
        userAuditRequest.setMethod(wrappedRequest.getMethod());

        userAuditRequest.setTimestamp(LocalDateTime.now());
        userAuditRequest.setRequestBody(new String(wrappedRequest.getBody())); // Save the body
        userAuditRequestRepository.save(userAuditRequest);

        // Proceed with the filter chain
        filterChain.doFilter(wrappedRequest, response);

        // Log response
        userAuditResponse.setUserAuditRequest(userAuditRequest);
        userAuditResponse.setTimestamp(LocalDateTime.now());
        userAuditResponse.setStatus(String.valueOf(response.getStatus()));
        userAuditResponse.setMessage(getResponseMessage(response));

        userAuditResponseRepository.save(userAuditResponse);
    }

    private String getResponseMessage(HttpServletResponse response) {
        // You can customize this to include more detailed messages based on status codes
        switch (response.getStatus()) {
            case 200:
                return "Success";
            case 201:
                return "Created";
            case 403:
                return "Forbidden: User does not have permission to access this resource";
            case 401:
                return "Unauthorized: Authentication required";
            default:
                return "Response logged with status: " + response.getStatus();
        }

    }

    public UserEntity getAuthenticatedUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserEntity) {
                return (UserEntity) principal; // Cast to your custom user type
            }
        }
        return null; // Return null or throw an exception if no user is found
    }
}