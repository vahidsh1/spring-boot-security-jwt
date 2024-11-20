package com.bezkoder.springjwt.filter;

import com.bezkoder.springjwt.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class FirstLoginFilter extends BasicAuthenticationFilter {

    public FirstLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            User user = (User) auth.getPrincipal(); // Assuming you're using UserDetails

            if (user.isFirstLogin() && !request.getRequestURI().equals("/auth/changepassword")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "First-time users must change their password.");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}