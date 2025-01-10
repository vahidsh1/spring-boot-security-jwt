package com.bezkoder.springjwt.controller.security;

import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//import static org.springframework.test.web.client.match;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class JwtFilterTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    public void whenMalformedJwtIsSent_thenReturnMalformedJwtError() throws Exception {
        String malformedJwt = "invalid.jwt.token";

        // Mock behavior for JWT utility
        Mockito.when(jwtUtils.validateJwtToken(malformedJwt)).thenThrow(new MalformedJwtException("Malformed JWT token"));

        // Perform the request with the malformed token
        mockMvc.perform(MockMvcRequestBuilders.get("/api/secure-endpoint")
                        .header("Authorization", "Bearer " + malformedJwt))
                .andExpect(status().isUnauthorized()) // Expect Unauthorized status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Invalid or malformed JWT token."));
    }

    @Test
    public void whenExpiredJwtIsSent_thenReturnJwtExpiredError() throws Exception {
        String expiredJwt = "expired.jwt.token";

        // Mock behavior for JWT utility
        Mockito.when(jwtUtils.validateJwtToken(expiredJwt)).thenThrow(new ExpiredJwtException(null, null, "JWT token is expired"));

        // Perform the request with the expired token
        mockMvc.perform(MockMvcRequestBuilders.get("/api/secure-endpoint")
                        .header("Authorization", "Bearer " + expiredJwt))
                .andExpect(status().isUnauthorized()) // Expect Unauthorized status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Invalid or malformed JWT token."));
    }
}
