//package com.bezkoder.springjwt.config;
//
//import com.bezkoder.springjwt.annotation.Loggable;
//import com.bezkoder.springjwt.entity.UserAuditRequest;
//import com.bezkoder.springjwt.repository.UserAuditRepository;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Service
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Autowired
//    private UserAuditRepository userAuditRepository; // Repository for UserAudit
//
//    @Loggable
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        response.setStatus(HttpServletResponse.SC_OK);
//        String username = authentication.getName();
//        String ipAddress = request.getRemoteAddr();
//        String userAgent = request.getHeader("User-Agent");
//        String deviceInformation = request.getRequestURI();
//        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//
//        UserAuditRequest userAuditRequest = new UserAuditRequest();
//        userAuditRequest.setTimestamp(System.currentTimeMillis());
//        userAuditRequest.setActionResult(true); // Successful login
//        userAuditRequest.setIpAddress(ipAddress);
//        userAuditRequest.setUserAgent(userAgent);
//        userAuditRequest.setDeviceInformation(deviceInformation);
//        userAuditRequest.setActionDetails("User logged in successfully");
//        // Set other fields as necessary
//
//        userAuditRepository.save(userAuditRequest);
//        // Proceed with the default success handler behavior
//    }
//}
////@Service
////public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
////    private AuthenticationSuccessHandler target = new SavedRequestAwareAuthenticationSuccessHandler();
////    @Autowired
////    private UserRepository userRepository; // Assume you have a UserService
////
////
////    @Override
////    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
////                                        Authentication authentication) throws IOException {
////        String username = authentication.getName();
////        Optional<User> user = userRepository.findByUsername(username); // Fetch user details
////
////        String ipAddress = request.getRemoteAddr();
////        String deviceInformation = request.getRequestURI();
////
////        System.out.println(ipAddress + ":" + request.getRemotePort() + " 11111111111111111111111111");
////        String userAgent = request.getHeader("User-Agent");
////        Long currentTime = System.currentTimeMillis();
////        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
////        Date date = new Date(currentTime);
////        String time = simpleDateFormat.format(date);
////        UserAudit userAudit = new
////                UserAudit(user.get(), time, authentication.isAuthenticated(), ipAddress, userAgent, deviceInformation);
////        System.out.println(userAudit.toString());
////
////    }
////}
//
//
