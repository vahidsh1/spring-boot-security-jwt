package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.payload.request.ChangePasswordRequest;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface BaseService {
    public JwtResponse loginUserService(LoginRequest loginRequest);
    public ResponseEntity<?> registerUserService(SignupRequest signUpRequest);
    public ResponseEntity<?> changePasswordService(ChangePasswordRequest changePasswordRequest) throws Exception;

}
