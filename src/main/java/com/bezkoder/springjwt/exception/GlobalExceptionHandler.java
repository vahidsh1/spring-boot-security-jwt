package com.bezkoder.springjwt.exception;

import com.bezkoder.springjwt.payload.ResponseCode;
import com.bezkoder.springjwt.payload.response.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.security.sasl.AuthenticationException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND,ResponseCode.USER_NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED,ResponseCode.UNAUTHORIZED);
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ApiResponse<Object>> handleSignatureException(IllegalArgumentException ex, WebRequest request) {
//        return buildErrorResponse(HttpStatus.UNAUTHORIZED,ResponseCode.);
//    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiResponse<Object>> handleSignatureException(SignatureException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED,ResponseCode.INVALID_SIGNATURE);
    }



    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiResponse<Object>> handleMalformedJwtException(MalformedJwtException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED,ResponseCode.MALFORMED_SIGNATURE);
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<Object>> handleSignatureException(JwtException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED,ResponseCode.MALFORMED_SIGNATURE);
    }


    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Object>> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.TOKEN_EXPIRED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.TOKEN_EXPIRED);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        // You can check if the exception is an instance of AuthenticationException or customize the response
        if (ex.getCause() instanceof AuthenticationException) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, ResponseCode.INVALID_TOKEN);
        }
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiResponse<Object>> buildErrorResponse( HttpStatus status,ResponseCode responseCode) {
        ApiResponse<Object> errorResponse = ApiResponse.error(status, responseCode);
        return new ResponseEntity<>(errorResponse, status);
    }
}


