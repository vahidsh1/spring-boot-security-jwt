package com.bezkoder.springjwt.controller.security;

import com.bezkoder.springjwt.config.AuthTokenFilter;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthTokenFilterTest {

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private MockHttpServletRequest request;

    @Mock
    private MockHttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private SecurityContextHolder securityContextHolder;

    @Test
    void shouldAuthenticateUserWhenValidJwtIsProvided() throws Exception {
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
    void shouldNotAuthenticateWhenJwtSignatureIsInvalid() throws Exception {
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
    void shouldContinueFilterChainWhenNoAuthorizationHeader() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtUtils, never()).validateJwtToken(anyString());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldHandleMalformedJwtTokenGracefully() throws Exception {
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
}
