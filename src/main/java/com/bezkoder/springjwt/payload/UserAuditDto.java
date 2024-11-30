package com.bezkoder.springjwt.payload;

import com.bezkoder.springjwt.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuditDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship to User entity (one-to-many)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private String actionDate;
    @Column(nullable = false)
    private String actionTime;
    @Column(nullable = false)
    private String actionResult;
    @Column(nullable = false)
    private String ipAddress;
    @Column(nullable = false)
    private String userAgent;
    @Column(nullable = false)
    private String deviceInformation;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String sessionId;
    @Column(nullable = false)
    private String actionDetails;
    @Column(nullable = false)
    private String oldValue;
    @Column(nullable = false)
    private String newValue;
    @Column(nullable = false)
    private String reasonForAction;

    // Getters and setters
}