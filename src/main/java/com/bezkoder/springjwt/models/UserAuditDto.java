package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class UserAuditDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship to User entity (one-to-many)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Other audit fields (as discussed earlier)
    private String actionDate;
    private String actionTime;
    private String actionResult;
    private String ipAddress;
    private String userAgent;
    private String deviceInformation;
    private String location;
    private String sessionId;
    private String actionDetails;
    private String oldValue;
    private String newValue;
    private String reasonForAction;

    // Getters and setters
}