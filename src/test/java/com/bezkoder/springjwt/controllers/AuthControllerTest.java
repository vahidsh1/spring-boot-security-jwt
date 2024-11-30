package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.entity.ERole;
import com.bezkoder.springjwt.entity.Role;
import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc

//@DataJpaTest
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthController authController;
    @Autowired
    WebMvcAutoConfiguration webMvcAutoConfiguration;

    @Test
    public void givenUserEntity_whenSaveUser_thenUserIsPersisted() throws Exception {
        Set set = new HashSet();
        Role role_admin = new Role(1, ERole.ROLE_ADMIN);
        Role role_moderator = new Role(2, ERole.ROLE_MODERATOR);
        Role role_user = new Role(3, ERole.ROLE_USER);
        set.add(role_admin);
        set.add(role_moderator);
        set.add(role_user);
        User user = new User(1L, "vahid", "vahidsh1@gmail.com",
                encoder.encode("123456"), true, set);
        userRepository.save(user);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"vahid\",\"password\":\"123456\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void givenAnyUserEntity_whenLogin_thenNotReturnAccessDenied() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"vahid\",\"password\":\"1q23456\"}")
                .contentType(MediaType.APPLICATION_JSON);
                MvcResult result = (MvcResult) mockMvc.perform(request).andExpect(status().isOk());
    }
}