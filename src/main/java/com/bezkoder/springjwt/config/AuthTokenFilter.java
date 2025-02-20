package com.bezkoder.springjwt.config;

import com.bezkoder.springjwt.handler.HandleErrorResponse;
import com.bezkoder.springjwt.security.jwt.JwtBlackList;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.stereotype.Component;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtBlackList jwtBlacklist;

    @Autowired
    private HandleErrorResponse handleErrorResponse;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

            // Check if JWT exists and is not blacklisted
            if (jwt != null) {
                if (jwtBlacklist.isTokenBlacklisted(jwt)) {
                    handleErrorResponse.handleErrorResponse(
                            response, HttpStatus.UNAUTHORIZED, "Token has been invalidated, please login again ", request.getPathInfo());
                    return;
                }

                // Validate the token
                if (jwtUtils.validateJwtToken(jwt)) {
                    String username = jwtUtils.getUserNameFromJwtToken(jwt);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            // Continue with the filter chain
            filterChain.doFilter(request, response);

        } catch (JwtException | ParseException e) {
            logger.error("Cannot set user authentication: " + e.getMessage());
            handleErrorResponse.handleErrorResponse(
                    response, HttpStatus.UNAUTHORIZED, e.getMessage(), request.getPathInfo());
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}

//public class AuthTokenFilter extends OncePerRequestFilter {
//    @Autowired
//    HandleErrorResponse handleErrorResponse;
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    public void doFilterInternal(HttpServletRequest request,
//                                 HttpServletResponse response,
//                                 FilterChain filterChain) throws ServletException, IOException {
//        try {
//            String jwt = parseJwt(request);
//            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//                String username = jwtUtils.getUserNameFromJwtToken(jwt);
//
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails, null, userDetails.getAuthorities());
//
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//            filterChain.doFilter(request, response);
//        } catch (JwtException | ParseException e) {
//            logger.error("Cannot set user authentication: " + e.getMessage());
//            handleErrorResponse.handleErrorResponse(response,HttpStatus.UNAUTHORIZED, e.getMessage(),request.getPathInfo());
//            // Stop further filter chain execution
//            return;
//        }
//    }
//
////    private void handleAuthError(HttpServletResponse response, String message) throws IOException {
////        response.setStatus(HttpStatus.UNAUTHORIZED.value());
////        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
////
////        Map<String, Object> errorDetails = new HashMap<>();
////        errorDetails.put("timestamp", System.currentTimeMillis());
////        errorDetails.put("status", HttpStatus.UNAUTHORIZED.value());
////        errorDetails.put("error", "Unauthorized");
////        errorDetails.put("message", message);
////        errorDetails.put("path", "/");
////
////        response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(errorDetails));
////    }
//
//    private String parseJwt(HttpServletRequest request) {
//        String headerAuth = request.getHeader("Authorization");
//
//        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
//            return headerAuth.substring(7);
//        }
//
//        return null;
//    }
//}
//
