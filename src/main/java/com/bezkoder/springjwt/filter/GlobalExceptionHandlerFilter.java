package com.bezkoder.springjwt.filter;

import com.bezkoder.springjwt.payload.ResponseCode;
import com.bezkoder.springjwt.payload.response.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;

public class GlobalExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain fc) throws ServletException, IOException {
        try {
            fc.doFilter(request, response); // Continue with the request
        } catch (AccessDeniedException e) {
            buildErrorResponse(response, HttpStatus.FORBIDDEN, ResponseCode.ACCESS_DENIED);
        } catch (AuthenticationException e) {
            buildErrorResponse(response, HttpStatus.UNAUTHORIZED, ResponseCode.UNAUTHORIZED);
        } catch (ExpiredJwtException e) {
            // Log error or handle redirection here
            buildErrorResponse(response, HttpStatus.UNAUTHORIZED, ResponseCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            // Handle other generic exceptions
            buildErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void buildErrorResponse(HttpServletResponse response, HttpStatus status, ResponseCode responseCode) {
        ApiResponse<Object> errorResponse = ApiResponse.error(status, responseCode);
        response.setStatus(status.value());
        response.setContentType("application/json");
        try {
            response.getWriter().write(errorResponse.toJson()); // Assuming ApiResponse has a toJson method
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
