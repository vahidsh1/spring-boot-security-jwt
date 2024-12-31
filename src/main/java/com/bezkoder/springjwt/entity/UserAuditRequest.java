package com.bezkoder.springjwt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user_audit_requests")
public class UserAuditRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;

    private String endpoint;
    private String httpMethod;

    @Column(columnDefinition = "TEXT")
    private String parameters;

    private String ipAddress;
    private int clientPort;
    private String jwtToken;
    private String sessionId;
    private LocalDateTime timestamp;

    // Many requests can be associated with one user
    @ManyToOne
    @JoinColumn(name = "users")
    private UserEntity userEntity;

    // One-to-one relationship with the response
    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL)
    private UserAuditResponse response;
}