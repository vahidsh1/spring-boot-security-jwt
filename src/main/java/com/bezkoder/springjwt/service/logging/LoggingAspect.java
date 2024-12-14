//package com.bezkoder.springjwt.service.logging;
//
//import com.bezkoder.springjwt.config.AuthTokenFilter;
//import com.bezkoder.springjwt.entity.UserAudit;
//import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
//import com.bezkoder.springjwt.service.audit.AuditLogService;
//import com.bezkoder.springjwt.service.audit.AuditLogServiceImpl;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestMapping;
//@Service
//@Aspect
////@EnableAspectJAutoProxy
//public class LoggingAspect {
//    @Autowired
//    AuthTokenFilter authTokenFilter;
//    @Autowired
//    UserDetailsServiceImpl userDetailsServiceImpl;
//    @Autowired
//    private AuditLogServiceImpl auditLogServiceImpl;
//
//    private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class.getName());
//
//    @After("@annotation(com.bezkoder.springjwt.annotation.Loggable)")
//    public void logAround() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("ffffffffffffffffffffffffffffffffffffffffffff");
//        System.out.println(authentication.getPrincipal());
//        long startTime = System.currentTimeMillis();
//        long endTime = System.currentTimeMillis();
//        // Extract relevant information
////        String apiMethod = joinPoint.getSignature().getName();
////        System.out.println(joinPoint.getSignature().getDeclaringType().getName());
////        String authenticationMethod = ((AuthTokenFilter) joinPoint.getSignature().getDeclaringType().getAnnotation(AuthTokenFilter.class)).toString();
////        String endpoint = ((RequestMapping) joinPoint.getSignature().getDeclaringType().getAnnotation(RequestMapping.class)).value()[0];
//        System.out.println("ffffffffffffffffffffffffffffffffffffffffffff");
//        // ... (Extract other details)
//        UserAudit userAudit = new UserAudit();
////        SecurityContextHolder.getContext().
////        userAudit.;
////        userAudit.setEndpoint(endpoint);
////        userAudit.setAuthenticationMethod(authenticationMethod);
////        userAudit.setEndpoint(endpoint);
////        // ... (Set other fields)
////        auditLogServiceImpl.saveLog(userAudit);
//
//        // Use logging library for additional logging
//        logger.info("API call took {} ms *****************************", (endTime - startTime));
//
//    }
//}