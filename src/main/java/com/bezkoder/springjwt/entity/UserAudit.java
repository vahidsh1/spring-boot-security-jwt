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
    @Column(nullable = false)

    private String ipAddress;
    private String userAgent;
    private String deviceInformation;
    private String location;
    @Column(nullable = false)

    private String sessionId;
    @Column(nullable = false)

    private String actionDetails;
    //    @Column(nullable = false)
//    private String oldValue;
//    @Column(nullable = false)
//    private String newValue;
//    @Column(nullable = false)
//    private String reasonForAction;
//
    private String message;

    private String apiMethod;

    private String endpoint;

    private String requestHeaders;
    @Column(nullable = false)

    private String requestBody;

    private Integer responseStatus;

    private String responseHeaders;

    private String responseBody;

    private String authenticationMethod;
    private String errorMessage;

    // Getters and Setters omitted for brevity

//    public UserAudit(String username, String actionTimeStamp, boolean actionResult,
//                     String ipAddress, String userAgent, String deviceInformation) {
//        this.username = username;
//        this.actionResult = actionResult;
//        this.actionTimeStamp = actionTimeStamp;
//        this.ipAddress = ipAddress;
//        this.userAgent = userAgent;
//        this.deviceInformation = deviceInformation;
//
//    }

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
//                ", oldValue='" + oldValue + '\'' +
//                ", newValue='" + newValue + '\'' +
//                ", reasonForAction='" + reasonForAction + '\'' +
                '}';
    }
// Getters and setters
}