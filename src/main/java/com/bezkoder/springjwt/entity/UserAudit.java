package com.bezkoder.springjwt.entity;

import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    // Relationship to User entity (one-to-many)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String actionTimeStamp;
    private boolean actionResult;

    private String ipAddress;
    private String userAgent;
    private String deviceInformation;
    private String location;

    private String sessionId;

    private String actionDetails;

    private String message;

    private String apiMethod;

    private String endpoint;

    private String requestHeaders;

    private String requestBody;

    private Integer responseStatus;

    private String responseHeaders;

    private String responseBody;

    private String authenticationMethod;
    private String errorMessage;

    @Override
    public String toString() {
        return "UserAudit{" +
                "id=" + id +
                ", user=" + user +
                ", actionTimeStamp='" + actionTimeStamp + '\'' +
                ", actionResult=" + actionResult +
                ", ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", deviceInformation='" + deviceInformation + '\'' +
                ", location='" + location + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", actionDetails='" + actionDetails + '\'' +
                '}';
    }
}