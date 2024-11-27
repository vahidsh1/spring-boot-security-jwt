package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
//@DataJpaTest
class AuthControllerTest {
    MockMvc mockMvc;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthController authController;
    @Autowired
    WebMvcAutoConfiguration webMvcAutoConfiguration;
//
    @Test
    public void givenUserEntity_whenSaveUser_thenUserIsPersisted() throws Exception {
        Set set = new HashSet();
        Role role_admin = new Role(1, ERole.ROLE_ADMIN);
        Role role_moderator = new Role(2, ERole.ROLE_MODERATOR);
        Role role_user = new Role(3, ERole.ROLE_USER);
        set.add(role_admin);
        set.add(role_moderator);
        set.add(role_user);
        User user = new User( "vahid", "vahidsh1@gmail.com",
                encoder.encode("123456"), true, set);
        userRepository.save(user);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"vahid\",\"password\":\"123456\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk()).andReturn();
    }

}