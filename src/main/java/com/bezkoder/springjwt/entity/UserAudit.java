package com.bezkoder.springjwt.payload;

import com.bezkoder.springjwt.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAudit {

    private Long id;
    // Relationship to User entity (one-to-many)
    private User user;
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