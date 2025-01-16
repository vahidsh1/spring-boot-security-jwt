package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.payload.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service

public interface AuthenticationService {
    Authentication authenticateUser(LoginRequest loginRequest);
}


