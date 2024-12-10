package com.bezkoder.springjwt.service.logging;

import com.bezkoder.springjwt.audit.AuditAspect;
import com.bezkoder.springjwt.config.AuthTokenFilter;
import com.bezkoder.springjwt.entity.UserAudit;
import com.bezkoder.springjwt.service.audit.AuditLogService;
import com.bezkoder.springjwt.service.audit.AuditLogServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
@Service
@Aspect
@EnableAspectJAutoProxy
public class LoggingAspect {

    @Autowired
    private AuditLogServiceImpl auditLogServiceImpl;
    private static Logger logger = LoggerFactory.getLogger(AuditAspect.class.getName());
//    @Around("@annotation(com.bezkoder.springjwt.annotation.*)")
    @After("execution(* org.springframework.security.authentication.Auth.*(..))")

    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        // Extract relevant information
        String apiMethod = joinPoint.getSignature().getName();
        String authenticationMethod = ((AuthTokenFilter) joinPoint.getSignature().getDeclaringType().getAnnotation(AuthTokenFilter.class)).toString();
        String endpoint = ((RequestMapping) joinPoint.getSignature().getDeclaringType().getAnnotation(RequestMapping.class)).value()[0];


        // ... (Extract other details)

        UserAudit userAudit = new UserAudit();
        userAudit.setApiMethod(apiMethod);
        userAudit.setEndpoint(endpoint);
        userAudit.setAuthenticationMethod(authenticationMethod);
        userAudit.setEndpoint(endpoint);
        // ... (Set other fields)

        auditLogServiceImpl.saveLog(userAudit);

        // Use logging library for additional logging
        logger.info("API call took {} ms *****************************", (endTime - startTime));

        return result;
    }
}