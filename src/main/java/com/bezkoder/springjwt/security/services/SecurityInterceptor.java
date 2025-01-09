//package com.bezkoder.springjwt.security.services;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
//import com.bezkoder.springjwt.entity.ERole;
//import com.bezkoder.springjwt.entity.Role;
//import com.bezkoder.springjwt.entity.UserEntity;
//import com.bezkoder.springjwt.entity.UserEntity;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//public class SecurityInterceptor implements HandlerInterceptor {
//
//    private static final Pattern AUTH_HEADER_PATTERN = Pattern.compile("Bearer (\\S+)");
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        // get the bearer token
//         String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
//        //
//         if (StringUtils.hasLength(authorization)) {
//         throw new AccessDeniedException("Authorization header is empty");
//         }
//         Matcher matcher = AUTH_HEADER_PATTERN.matcher(authorization);
//         if (!matcher.matches()) {
//         throw new AccessDeniedException(String.format("Invalid authorization header: [%s]", authorization));
//         }
//         String accessToken = matcher.group(1);
//
//        // get the user from the bearer token
////         Long userId = getUserIdByAccessToken(accessToken);
//
//        UserEntity currentUser = new UserEntity();
//        currentUser.setUsername("czetsuyatech.com");
////        currentUser.setRoles(new HashSet<>(Arrays.asList()));
//
//        // catch null user here
//
//        // use for security
////        UserThreadLocalHolder.set(currentUser);
//
//        // use to fetch the user info
//        request.setAttribute("currentUserId", 1L);
//
//        return true;
//    }
//}