//package com.bezkoder.springjwt.exception;
//
//import com.bezkoder.springjwt.payload.ResponseCode;
//import com.bezkoder.springjwt.payload.response.ApiResponse;
//import io.jsonwebtoken.ExpiredJwtException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(AccessDeniedException ex) {
//        return buildErrorResponse(HttpStatus.FORBIDDEN, ResponseCode.ACCESS_DENIED);
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
//        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ResponseCode.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<ApiResponse<Object>> handleExpiredJwtException(ExpiredJwtException ex) {
//        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ResponseCode.TOKEN_EXPIRED);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
//        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR);
//    }
//
//    private ResponseEntity<ApiResponse<Object>> buildErrorResponse(HttpStatus status, ResponseCode responseCode) {
//        ApiResponse<Object> errorResponse = ApiResponse.error(status, responseCode);
//        return new ResponseEntity<>(errorResponse, status);
//    }
//}
