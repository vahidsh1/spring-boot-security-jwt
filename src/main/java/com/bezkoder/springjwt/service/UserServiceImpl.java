//package com.bezkoder.springjwt.service;
//
//import com.bezkoder.springjwt.entity.User;
//import com.bezkoder.springjwt.exception.UserNotFoundException;
//import com.bezkoder.springjwt.repository.UserRepository;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public List<User> getUsers() {
//        return userRepository.findAll();
//    }
//
//    @Override
//    public Optional<User> getUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    @Override
//    public boolean hasUserWithUsername(String username) {
//        return userRepository.existsByUsername(username);
//    }
//
//    @Override
//    public boolean hasUserWithEmail(String email) {
//        return userRepository.existsByEmail(email);
//    }
//
//    @Override
//    public User validateAndGetUserByUsername(String username) {
//        return getUserByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", username)));
//    }
//
//    @Override
//    public User saveUser(User user) {
//        return userRepository.save(user);
//    }
//
//    @Override
//    public void deleteUser(User user) {
//        userRepository.delete(user);
//    }
//}
