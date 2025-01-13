package com.bezkoder.springjwt.controller;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.payload.ResponseCode;
import com.bezkoder.springjwt.payload.request.ChangePasswordRequest;
import com.bezkoder.springjwt.payload.response.ApiResponse;

import com.bezkoder.springjwt.security.jwt.JwtBlackList;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.springjwt.entity.ERole;
import com.bezkoder.springjwt.entity.Role;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtBlackList jwtBlacklist;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, ResponseCode.SUCCESS, new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.isFirstLogin(),
                roles)));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        String token = parseJwt(request);

        if (token != null && jwtUtils.validateJwtToken(token)) {
            long expirationTime = jwtUtils.getExpirationFromToken(token).getTime() - System.currentTimeMillis();
            jwtBlacklist.addTokenToBlacklist(token, expirationTime);
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, ResponseCode.SUCCESS, "Logout successful"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(HttpStatus.UNAUTHORIZED, ResponseCode.INVALID_TOKEN));
    }
//    @PostMapping("/logout")
//    public ResponseEntity<?> logoutUser(HttpServletRequest request) throws ParseException {
//        String token = parseJwt(request);
//
//        if (token != null && jwtUtils.validateJwtToken(token)) {
//            // Extract the username from the token
//            String tokenUsername = jwtUtils.getUserNameFromJwtToken(token);
//
//            // Get the currently authenticated user from SecurityContext
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(ApiResponse.error(HttpStatus.UNAUTHORIZED, ResponseCode.UNAUTHORIZED));
//            }
//
//            String authenticatedUsername = authentication.getName();
//
//            // Ensure the token belongs to the authenticated user
//            if (!tokenUsername.equals(authenticatedUsername)) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                        .body(ApiResponse.error(HttpStatus.FORBIDDEN, ResponseCode.INVALID_TOKEN_OWNER));
//            }
//
//            // Calculate remaining expiration time and blacklist the token
//            long expirationTime = jwtUtils.getExpirationFromToken(token).getTime() - System.currentTimeMillis();
//            jwtBlacklist.addTokenToBlacklist(token, expirationTime);
//
//            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, ResponseCode.SUCCESS, "Logout successful"));
//        }
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(HttpStatus.UNAUTHORIZED, ResponseCode.INVALID_TOKEN));
//    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

        return (headerAuth != null && headerAuth.startsWith("Bearer ")) ? headerAuth.substring(7) : null;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (!userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error(HttpStatus.UNAUTHORIZED, ResponseCode.UNAUTHORIZED));
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST, ResponseCode.USERNAME_ALREADY_EXISTS));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND, ResponseCode.USER_NOT_FOUND));
        }

        Set<Role> roles = new HashSet<>();
        Set<String> strRoles = signUpRequest.getRole();

        if (strRoles == null || strRoles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST, ResponseCode.ROLE_NOT_FOUND));
        }

        for (String role : strRoles) {
            Role fetchedRole = roleRepository.findByName(ERole.valueOf(role.toUpperCase()))
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(fetchedRole);
        }

        UserEntity userEntity = new UserEntity(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail(), true, roles);

        userRepository.save(userEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, ResponseCode.SUCCESS, userEntity));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> passwordChange(@RequestBody ChangePasswordRequest changePasswordRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (changePasswordRequest.getOldPassword().equals(changePasswordRequest.getNewPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(ApiResponse.error(HttpStatus.NOT_ACCEPTABLE, ResponseCode.PASSWORD_IS_SAME_AS_BEFORE));
        }

        if (!userDetails.getUsername().equals(changePasswordRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(ApiResponse.error(HttpStatus.NOT_ACCEPTABLE, ResponseCode.INVALID_USERNAME_PASSWORD_CHANGE_REQUEST));
        }

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!encoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(ApiResponse.error(HttpStatus.NOT_ACCEPTABLE, ResponseCode.CURRENT_PASSWORD_MISMATCH));
        }

        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        if (((UserDetailsImpl) authentication.getPrincipal()).isFirstLogin()) {
            user.setFirstLogin(false);
        }

        userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, ResponseCode.PASSWORD_CHANGED_SUCCESSFULLY, changePasswordRequest));
    }
}
//public class AuthController {
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    RoleRepository roleRepository;
//
//    @Autowired
//    PasswordEncoder encoder;
//
//    @Autowired
//    JwtUtils jwtUtils;
//
//    @PostMapping("/login")
////    @Loggable
//    public ResponseEntity<ApiResponse<Object>> authenticateUser(@RequestBody LoginRequest loginRequest) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        String jwt = jwtUtils.generateJwtToken(authentication);
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, ResponseCode.SUCCESS, new JwtResponse(jwt,
//                userDetails.getId(),
//                userDetails.getUsername(),
//                userDetails.getEmail(),
//                userDetails.isFirstLogin(),
//                roles)));
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        if (!userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                    .body(ApiResponse.error(HttpStatus.UNAUTHORIZED, ResponseCode.UNAUTHORIZED));
//        }
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST, ResponseCode.USERNAME_ALREADY_EXISTS));
//        }
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(ApiResponse.error(HttpStatus.NOT_FOUND, ResponseCode.USER_NOT_FOUND));
//        }
//        Set<String> strRoles = signUpRequest.getRole();
////        Set<Role> roles = new HashSet<>();
////        if (strRoles == null) {
////            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
////                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////            roles.add(userRole);
////            return ResponseEntity.status(HttpStatus.CREATED)
////                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST, ResponseCode.ROLE_NOT_FOUND));
////        } else {
////            strRoles.forEach(role -> {
////                switch (role) {
////                    case "admin":
////                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
////                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////
////                        roles.add(adminRole);
////
////                        break;
////                    case "mod":
////                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
////                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////
////                        roles.add(modRole);
////
////                        break;
////                    default:
////                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
////                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////                        roles.add(userRole);
////                }
////            });
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null || strRoles.isEmpty()) {
//            // Handle case where no roles are provided
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST, ResponseCode.ROLE_NOT_FOUND));
//        }
//
//        for (String role : strRoles) {
//            try {
//                Role fetchedRole;
//                switch (role.toLowerCase()) {
//                    case "admin":
//                        fetchedRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        break;
//                    case "mod":
//                        fetchedRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        break;
//                    case "user":
//                        fetchedRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        break;
//                    default:
//                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, ResponseCode.ROLE_NOT_FOUND));
//                }
//                roles.add(fetchedRole);
//            } catch (RuntimeException ex) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(ApiResponse.error(HttpStatus.BAD_REQUEST, ResponseCode.ROLE_NOT_FOUND));
//            }
//        }
//
//        // Create new user's account
//        UserEntity userEntity = new UserEntity(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
//                signUpRequest.getEmail(), true, roles);
//        userRepository.save(userEntity);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(ApiResponse.success(HttpStatus.CREATED, ResponseCode.SUCCESS, userEntity));
//    }
//
//    @PostMapping("/change-password")
//    public ResponseEntity<?> passwordChange(@RequestBody ChangePasswordRequest changePasswordRequest) throws
//            Exception {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        if (changePasswordRequest.getOldPassword().equals(changePasswordRequest.getNewPassword())) {
//
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//                    .body(ApiResponse.error(HttpStatus.NOT_ACCEPTABLE, ResponseCode.PASSWORD_IS_SAME_AS_BEFORE));
//
//        }
//        if (!userDetails.getUsername().equals(changePasswordRequest.getUsername())) {
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//                    .body(ApiResponse.error(HttpStatus.NOT_ACCEPTABLE, ResponseCode.INVALID_USERNAME_PASSWORD_CHANGE_REQUEST));
//        }
//        // Find the user by username or throw an exception if not found
//        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        // Check if the current password matches
//        if (!encoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//                    .body(ApiResponse.error(HttpStatus.NOT_ACCEPTABLE, ResponseCode.CURRENT_PASSWORD_MISMATCH));
////            throw new Exception("Current password is incorrect");
//        }
//
//        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
//        if (((UserDetailsImpl) (authentication).getPrincipal()).isFirstLogin()) {
//            user.setFirstLogin(false);
//        }
//        userRepository.save(user);
//        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//                .body(ApiResponse.success(HttpStatus.OK, ResponseCode.PASSWORD_CHANGED_SUCCESSFULLY, changePasswordRequest));
//    }
//}
//
//
//
