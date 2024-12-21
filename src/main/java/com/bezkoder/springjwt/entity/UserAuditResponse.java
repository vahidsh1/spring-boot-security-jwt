package com.bezkoder.springjwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserAuditResponse {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "request_id", unique = true) // Ensure unique request_id for one-to-one mapping
    private UserAuditRequest userAuditRequest;

    public UserAuditResponse(UserAuditRequest userAuditRequest) {
        this.userAuditRequest = userAuditRequest;
    }

    private String status; // e.g., SUCCESS, FAILURE
    private String message; // Response message
    private LocalDateTime timestamp;
}


