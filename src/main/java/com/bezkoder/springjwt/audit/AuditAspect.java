package com.bezkoder.springjwt.audit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {



    @Around("execution(* com.bezkoder.springjwt.*.*(..))")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // ... extract relevant information from joinPoint
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        // ... create and persist audit record
        return joinPoint.proceed();
    }
}