package com.bezkoder.springjwt.payload.response;

import com.bezkoder.springjwt.payload.ResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.engine.jdbc.mutation.TableInclusionChecker;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.Instant;

import java.time.Instant;
@Data
public class ApiResponse<T> {
    private HttpStatus status;
    private T data;
    private ResponseCode responseCode;
    private Long timestamp;
    private String reasonCode;
    private String description;
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this); // Converts the ApiResponse object to JSON string
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
            return "{}"; // Return an empty JSON object on error
        }
    }
    public ApiResponse(HttpStatus status,  T data, ResponseCode responseCode) {
        this.status = status;
        this.data = data;
        this.responseCode=responseCode;
        this.reasonCode = responseCode.getCode();
        this.timestamp = System.currentTimeMillis();
        this.description=responseCode.getMessage();
    }

    public static <T> ApiResponse<T> success(HttpStatus status, ResponseCode responseCode, T data) {
        return new ApiResponse<>(status, data, responseCode);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, ResponseCode responseCode) {
        return new ApiResponse<>(status, null, responseCode);
    }


}

