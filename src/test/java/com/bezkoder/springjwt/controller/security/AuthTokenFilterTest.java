package com.bezkoder.springjwt.controller.security;

import com.bezkoder.springjwt.config.AuthTokenFilter;
import com.bezkoder.springjwt.entity.ERole;
import com.bezkoder.springjwt.entity.Role;
import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserAuditRequestRepository;
import com.bezkoder.springjwt.repository.UserAuditResponseRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.text.ParseException;
import java.util.*;

import static groovy.json.JsonOutput.toJson;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(MockitoExtension.class)
@WebMvcTest
public class AuthTokenFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

//    private String validJwt;
//    private String adminJwt;
//    private String invalidJwt;
//    private String expiredJwt;
//    private String malformedJwt;

    @InjectMocks
    private AuthTokenFilter authTokenFilter;
    //    @Mock
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    UserRepository userRepository;
    @MockBean
    RoleRepository roleRepository;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    UserAuditRequestRepository userAuditRequestRepository;
    @MockBean
    UserAuditResponseRepository userAuditResponseRepository;
    @Mock
    private MockHttpServletRequest request;

    @Mock
    private MockHttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private SecurityContextHolder securityContextHolder;

    @BeforeEach
    public void setUp() throws ParseException {
        // Initialize JWT strings


        Role adminRole = new Role(1, ERole.ROLE_ADMIN); // Replace ERole.ROLE_ADMIN with your enum or string
        Role userRole = new Role(1, ERole.ROLE_USER); // Replace ERole.ROLE_ADMIN with your enum or string
        Role userModerator = new Role(1, ERole.ROLE_MODERATOR); // Replace ERole.ROLE_ADMIN with your enum or string
        // Mock behavior of roleRepository
        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(adminRole));
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(roleRepository.findByName(ERole.ROLE_MODERATOR)).thenReturn(Optional.of(userModerator));
        UserDetails userDetails = mock(UserDetails.class);
//        when(request.getHeader("Authorization")).thenReturn("Bearer " + validJwt);
//        when(jwtUtils.validateJwtToken(validJwt)).thenReturn(true);
//        when(jwtUtils.validateJwtToken(adminJwt)).thenReturn(true);
//        when(jwtUtils.getUserNameFromJwtToken(validJwt)).thenReturn(testUsername);
//        when(jwtUtils.getUserNameFromJwtToken(adminJwt)).thenReturn(adminUsername);
//        when(userDetailsService.loadUserByUsername(testUsername)).thenReturn(userDetails);
//        when(userDetailsService.loadUserByUsername(adminUsername)).thenReturn(userDetails);
        String validJwt = "valid.jwt.token";
        String expiredJwt = "expired.jwt.token";
        String malformedJwt = "malformed.jwt.token";
        String invalidJwt = "invalid.jwt.token";
        // Mock behavior for JwtUtils
        Mockito.when(jwtUtils.validateJwtToken(validJwt)).thenReturn(true);
        Mockito.when(jwtUtils.validateJwtToken(invalidJwt)).thenReturn(false);
        Mockito.when(jwtUtils.validateJwtToken(expiredJwt)).thenThrow(new io.jsonwebtoken.ExpiredJwtException(null, null, "JWT expired"));
        Mockito.when(jwtUtils.validateJwtToken(malformedJwt)).thenThrow(new io.jsonwebtoken.JwtException("Malformed JWT"));
//        Mockito.when(authenticationManager.authenticate(any())).thenReturn((Authentication) new Object());
//        Mockito.when(userRepository.findByUsername(any())).thenReturn(Optional.of(new UserEntity()));
//        Mockito.when(userRepository.findByUsername(any())).thenReturn(Optional.of(new UserEntity()));

        Mockito.when(jwtUtils.getUserNameFromJwtToken(validJwt)).thenReturn("vahid");
    }

//    @BeforeEach
//    public void setUp() throws ParseException {
//        // Mock behavior only for what's used in the test
//
//        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(new Role(1, ERole.ROLE_ADMIN)));
//        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(1, ERole.ROLE_USER)));
//
//        // Mock JWT validation only if used
//        when(jwtUtils.validateJwtToken(validJwt)).thenReturn(true);
//        when(jwtUtils.validateJwtToken(adminJwt)).thenReturn(true);
//        when(jwtUtils.validateJwtToken(expiredJwt)).thenThrow(new io.jsonwebtoken.ExpiredJwtException(null, null, "JWT expired"));
//        when(jwtUtils.validateJwtToken(malformedJwt)).thenThrow(new io.jsonwebtoken.JwtException("Malformed JWT"));
//
//        // Mock UserDetailsService only if it's necessary for the test
//        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(mock(UserDetails.class));
//        when(userDetailsService.loadUserByUsername("admin")).thenReturn(mock(UserDetails.class));
//
//        // Mock JWT Usernames if needed
//        Mockito.when(jwtUtils.getUserNameFromJwtToken(validJwt)).thenReturn("vahid");
//    }


    @Test
    public void shouldHandleExpiredJwtGracefully() throws Exception {
        // Arrange
        String expiredJwt = "expired.jwt.token";
        when(request.getHeader(any())).thenReturn("Bearer " + expiredJwt);
        when(jwtUtils.validateJwtToken(expiredJwt)).thenThrow(new ExpiredJwtException(null, null, "JWT expired"));


        // Mocking ServletOutputStream
        ServletOutputStream servletOutputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtUtils).validateJwtToken(expiredJwt);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(servletOutputStream).write(any(byte[].class)); // Ensure the error response is written
        verify(filterChain, never()).doFilter(request, response);
    }


    @Test
    public void shouldAuthenticateUserWhenValidJwtIsProvided() throws Exception {
        // Arrange
        String validJwt = "valid.jwt.token";
        String username = "testUser";
        UserDetails userDetails = mock(UserDetails.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validJwt);
        when(jwtUtils.validateJwtToken(validJwt)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(validJwt)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);
        // Assert
        verify(jwtUtils).validateJwtToken(validJwt);
        verify(securityContextHolder.getContext()).setAuthentication(any(Authentication.class));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateWhenJwtIsExpired() throws Exception {
        // Arrange
        String expiredJwt = "expired.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + expiredJwt);
        when(jwtUtils.validateJwtToken(expiredJwt)).thenThrow(new ExpiredJwtException(null, null, "JWT expired"));
        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);
        // Assert
        verify(jwtUtils).validateJwtToken(expiredJwt);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    public void shouldNotAuthenticateWhenJwtSignatureIsInvalid() throws Exception {
        // Arrange
        String invalidJwt = "invalid.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidJwt);
        when(jwtUtils.validateJwtToken(invalidJwt)).thenThrow(new SignatureException("Invalid signature"));
        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);
        // Assert
        verify(jwtUtils).validateJwtToken(invalidJwt);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    public void shouldContinueFilterChainWhenNoAuthorizationHeader() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);
        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);
        // Assert
        verify(jwtUtils, never()).validateJwtToken(anyString());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void shouldHandleMalformedJwtTokenGracefully() throws Exception {
        // Arrange
        String malformedJwt = "malformed.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + malformedJwt);
        when(jwtUtils.validateJwtToken(malformedJwt)).thenThrow(new MalformedJwtException("Malformed JWT"));

        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtUtils).validateJwtToken(malformedJwt);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }


    @Test
    public void testMalformedJwtToken() throws Exception {
        String malformedJwt = "malformed.jwt.token";
        // Simulate a request with a malformed JWT token
        ResultActions result = mockMvc.perform(get("/api/protected-endpoint")
                .header("Authorization", "Bearer " + malformedJwt)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        result.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid or malformed JWT token."));
    }

    @Test
    public void testExpiredJwtToken() throws Exception {
        // Simulate a request with an expired JWT token

        String expiredJwt = "expired.jwt.token";
        ResultActions result = mockMvc.perform(post("/api/auth/signup")
                .header("Authorization", "Bearer " + expiredJwt)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        result.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("JWT token expired"));
    }

    @Test
    @WithMockUser(username = "vahid", roles = "ADMIN")
    public void testValidJwtToken() throws Exception {
        String validJwt = "valid.jwt.token";

        // Create the SignupRequest object
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        SignupRequest signupRequest = new SignupRequest("hamind", "hamid1@gmail.com", roles, "123456");

        // Serialize the SignupRequest object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String signupRequestJson = objectMapper.writeValueAsString(signupRequest);

        // Simulate a request with a valid JWT token
        ResultActions result = mockMvc.perform(post("/api/auth/signup")
                .header("Authorization", "Bearer " + validJwt) // Add your valid JWT token
                .with(csrf())  // Include CSRF token
                .content(signupRequestJson) // Set serialized JSON content
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        result.andExpect(status().isCreated()); // Expect status 201 for successful creation
    }


}
