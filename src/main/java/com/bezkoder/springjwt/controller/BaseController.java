package com.bezkoder.springjwt.controller;


import com.bezkoder.springjwt.payload.ResponseCode;
import com.bezkoder.springjwt.payload.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public abstract class BaseController {

    // Common method to send success response with a generic data type
    protected <T> ResponseEntity<ApiResponse<T>> sendSuccessResponse(HttpStatus status, T data, ResponseCode message) {
        return ResponseEntity.ok(ApiResponse.success(status, message, data));
    }

    // Common method to send error response with a generic data type
    protected <T> ResponseEntity<ApiResponse<T>> sendErrorResponse(HttpStatus status, ResponseCode message) {
        return ResponseEntity.status(status).body(ApiResponse.error(status, message));
    }
}
