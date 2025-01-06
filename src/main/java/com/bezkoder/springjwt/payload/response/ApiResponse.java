package com.bezkoder.springjwt.payload.response;

import com.bezkoder.springjwt.payload.ResponseCode;
import org.springframework.http.HttpStatus;

import java.time.Instant;

import java.time.Instant;

public class ApiResponse<T> {
    private HttpStatus status;
    private T data;
    private ResponseCode responseCode;
    private Instant timestamp;

    public ApiResponse(HttpStatus status,  T data, ResponseCode responseCode) {
        this.status = status;
        this.data = data;
        this.responseCode = responseCode;
        this.timestamp = Instant.now();
    }

    public static <T> ApiResponse<T> success(HttpStatus status, ResponseCode responseCode, T data) {
        return new ApiResponse<>(status, data, responseCode);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, ResponseCode responseCode) {
        return new ApiResponse<>(status, null, responseCode);
    }

    public HttpStatus getStatus() {
        return status;
    }



    public T getData() {
        return data;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}

