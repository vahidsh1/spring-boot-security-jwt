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
public class UserAuditRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endpoint;
    private String IpAddress;
    private String method;
    private String username;
    private LocalDateTime timestamp;
    private String requestBody;
    // Relationship to User entity (one-to-many)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public UserAuditRequest( UserEntity user) {
        this.user = user;
    }

}