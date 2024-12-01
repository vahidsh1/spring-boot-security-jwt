package com.bezkoder.springjwt.config;

import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.entity.UserAudit;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.utils.TimeUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private AuthenticationSuccessHandler target = new SavedRequestAwareAuthenticationSuccessHandler();
    @Autowired
    private UserRepository userRepository; // Assume you have a UserService

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("user", authUser);
        session.setAttribute("username", authUser.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());

        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);
        //since we have created our custom success handler, its up to us, to where
        //we will redirect the user after successfully loginSavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        //String requestUrl = savedRequest.getRedirectUrl();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username); // Fetch user details
        String ipAddress = request.getRemoteAddr();
        String deviceInformation = request.getRequestURI();
        System.out.println(ipAddress + ":" + request.getRemotePort() + " 11111111111111111111111111");
        String userAgent = request.getHeader("User-Agent");
        Long currentTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date date = new Date(currentTime);
        String time = simpleDateFormat.format(date);
        UserAudit userAudit = new
                UserAudit(user.get(), time, authentication.isAuthenticated(), ipAddress, userAgent, deviceInformation);
        System.out.println(userAudit.toString());
    }
}
//@Service
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//    private AuthenticationSuccessHandler target = new SavedRequestAwareAuthenticationSuccessHandler();
//    @Autowired
//    private UserRepository userRepository; // Assume you have a UserService
//
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException {
//        String username = authentication.getName();
//        Optional<User> user = userRepository.findByUsername(username); // Fetch user details
//
//        String ipAddress = request.getRemoteAddr();
//        String deviceInformation = request.getRequestURI();
//
//        System.out.println(ipAddress + ":" + request.getRemotePort() + " 11111111111111111111111111");
//        String userAgent = request.getHeader("User-Agent");
//        Long currentTime = System.currentTimeMillis();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        Date date = new Date(currentTime);
//        String time = simpleDateFormat.format(date);
//        UserAudit userAudit = new
//                UserAudit(user.get(), time, authentication.isAuthenticated(), ipAddress, userAgent, deviceInformation);
//        System.out.println(userAudit.toString());
//
//    }
//}


