package com.bezkoder.springjwt.controller.security;

import com.bezkoder.springjwt.annotation.Security;
import com.bezkoder.springjwt.entity.ERole;
import com.bezkoder.springjwt.entity.Role;
import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@SpringBootTest
@WebMvcTest
public class SecurityConfigTest {
    @Mock
    JwtUtils jwtUtils;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void before() throws Exception {
        Set<Role> roleSet = new HashSet<Role>();
        UserEntity user = new UserEntity("admin", "example@gmail.com", "123456", true, null);
        when(jwtUtils.validateJwtToken(anyString())).thenReturn(Boolean.TRUE);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

    }
    @Test
    void whenAccessLogin_thenPermitted() throws Exception {
        mockMvc.perform(get("/api/auth/login"))
                .andExpect(status().isOk());
    }
    @Test
    void whenAccessSignupWithoutRole_thenForbidden() throws Exception {
        mockMvc.perform(post("/api/auth/signup"))
                .andExpect(status().isForbidden());
    }

    @Test
    void whenAccessH2Console_thenPermitted() throws Exception {
        mockMvc.perform(get("/h2-console/"))
                .andExpect(status().isOk());
    }

    @Test
    void whenAccessRestrictedEndpoint_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/protected"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void whenWrongUserName() throws Exception {

    }

}
