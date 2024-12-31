package com.bezkoder.springjwt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "user_audit_responses")
public class UserAuditResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long responseId;

    @OneToOne
    @JoinColumn(name = "request_id")
    private UserAuditRequest request;

    private int statusCode;

    @Column(columnDefinition = "TEXT")
    private String responseBody;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    private String jwtToken;
    private LocalDateTime timestamp;
}

