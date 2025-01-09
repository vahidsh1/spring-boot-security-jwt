package com.bezkoder.springjwt.controller.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class WebSecurityBeansTest {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Test
    void testAuthenticationManagerBean() {
        assertNotNull(authenticationManager);
    }

    @Test
    void testPasswordEncoderBean() {
        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    void testDaoAuthenticationProvider() {
        assertNotNull(daoAuthenticationProvider.getUserCache());
    }
}
