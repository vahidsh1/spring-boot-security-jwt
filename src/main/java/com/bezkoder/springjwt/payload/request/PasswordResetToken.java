package com.bezkoder.springjwt.payload.request;

import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.entity.UserEntity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    private Date expiryDate;
}