//package com.bezkoder.springjwt.runner;
//
//import com.bezkoder.springjwt.entity.User;
//import com.bezkoder.springjwt.service.UserService;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class DatabaseInitializer implements CommandLineRunner {
//
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) {
//        if (!userService.getUsers().isEmpty()) {
//            return;
//        }
//        USERS.forEach(user -> {
//            user.(passwordEncoder.encode(user()));
//            userService.saveUser(user);
//        });
//        log.info("Database initialized");
//    }
//
//    private static final List<User> USERS = Arrays.asList(
//            new User(1,'vahid', '$2y$10$8QlFvBG4s75EC3cXWdtJ6./w3aATwGhm4PEZvByY3jMWsGbB8Pg96', 'vahidsh1@gmail.com');
//            new User(2,'hamid', '$2y$10$8QlFvBG4s75EC3cXWdtJ6./w3aATwGhm4PEZvByY3jMWsGbB8Pg96', 'hamidsh1@gmail.com');
//    );
//    private static final List<Role> ROLES = Arrays.asList(
//            new Role("ROLE_USE");
//            new Role("ROLE_MODERATOR");
//            new Role("ROLE_ADMIN");
//    );

//}
