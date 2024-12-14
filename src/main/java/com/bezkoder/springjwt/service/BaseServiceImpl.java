package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.annotation.Loggable;
import com.bezkoder.springjwt.entity.ERole;
import com.bezkoder.springjwt.entity.Role;
import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.payload.request.ChangePasswordRequest;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BaseServiceImpl implements BaseService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    Authentication authentication;
    @Autowired
    JwtUtils jwtUtils;
    @Loggable
    public JwtResponse loginUserService(LoginRequest loginRequest) {
         authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.isFirstLogin(),
                roles);
    }
    @Loggable

    public ResponseEntity<?> registerUserService(SignupRequest signUpRequest) {
         authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        if (!userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
//                    new MessageResponse("You are not authorized to signup users."));
//        }
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), true, roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @Loggable

    public ResponseEntity<?> changePasswordService(ChangePasswordRequest changePasswordRequest) throws Exception {
         authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getUsername().equals(changePasswordRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: You are not authorize to changing password for requested username!"));
        }
        if (changePasswordRequest.getOldPassword().equals(changePasswordRequest.getNewPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Your new password and the old one is the same please use new one!!!"));
        }

        // Find the user by username or throw an exception if not found
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Check if the current password matches
        if (!encoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new Exception("Current password is incorrect");
        }

        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        if (((UserDetailsImpl) (authentication).getPrincipal()).isFirstLogin()) {
            user.setFirstLogin(false);
        }
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Your password changed successfully!"));
    }
}