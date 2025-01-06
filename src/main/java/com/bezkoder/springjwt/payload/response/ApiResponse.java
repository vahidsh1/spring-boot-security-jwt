package com.bezkoder.springjwt.payload.response;

import com.bezkoder.springjwt.payload.ResponseCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

import java.time.Instant;
@Data
public class ApiResponse<T> {
    private HttpStatus status;
    private T data;
    private ResponseCode responseCode;
    private Instant timestamp;
    private String reasonCode;
    private String description;

    public ApiResponse(HttpStatus status,  T data, ResponseCode responseCode) {
        this.status = status;
        this.data = data;
        this.responseCode=responseCode;
        this.reasonCode = responseCode.getCode();
        this.timestamp = Instant.now();
        this.description=responseCode.getMessage();
    }

    public static <T> ApiResponse<T> success(HttpStatus status, ResponseCode responseCode, T data) {
        return new ApiResponse<>(status, data, responseCode);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, ResponseCode responseCode) {
        return new ApiResponse<>(status, null, responseCode);
    }


}

