package com.bezkoder.springjwt.audit;

import com.bezkoder.springjwt.config.AuthTokenFilter;
import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
import org.apache.juli.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class AuditAspect {
    @Autowired
    AuthTokenFilter authTokenFilter;
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    private static Logger logger = LoggerFactory.getLogger(AuditAspect.class.getName());
    @Aspect
    @Component
    public class MyAspect {

        @After("execution(* com.bezkoder.springjwt.config.AuthTokenFilter.doFilter(..))")
        public void afterFilterExecution2() {
            logger.info(SecurityContextHolder.getContext().toString());
            // ... your logic here
        }

        //    @Pointcut("@annotation(Log)")
//        @After("execution(* com.bezkoder.springjwt.security.services.UserDetailsServiceImpl.*(..))")
////    @Before("execution(* com.xyz.dao.*.*(..))")
//        public void logPointcut() {
//            System.out.println("222222&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//
//            userDetailsServiceImpl.toString();
//            authTokenFilter.toString();
//            SecurityContext context = SecurityContextHolder.getContext();
//            System.out.println(context.toString() + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//        }

        @After("execution(* org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.*(..))")
//    @Before("execution(* com.xyz.dao.*.*(..))")
        public void ffffff() {
            System.out.println("222222&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

//            userDetailsServiceImpl.loadUserByUsername();
            authTokenFilter.toString();
            SecurityContext context = SecurityContextHolder.getContext();
            System.out.println(context.toString() + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        }


        @After("execution(* org.springframework.security.authentication.AuthenticationManager.*(..))")
        public void afterFilterExecution() {
            authTokenFilter.toString();
            SecurityContext context = SecurityContextHolder.getContext();
            // Extract information from context (e.g., Authentication)
            Authentication authentication = context.getAuthentication();
            if (authentication != null) {
                System.out.println("Extracted username:  ****************** " + authentication.getName());
            } else {
                System.out.println("No authentication found O)()I)I)UI)I)IU)I)");
            }
        }
    }
//    @Before("execution(* org.springframework.security.web.FilterChainProxy.*(..))")
//        public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
//            // start stopwatch
//            Object retVal = pjp.proceed();
//            // stop stopwatch
//        System.out.println("&&&&&&&&%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//            return retVal;
//        }

//    @After("execution(* org.springframework.security.web.context.SecurityContextHolderFilter.*(..))")
//    public Object logAroundSecurity(JoinPoint jp) throws Throwable {
//        // start stopwatch
//        Object retVal = jp.toString();
//        // stop stopwatch
//        System.out.println("&&&&&&&&%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//        return retVal;
//    }
//    @Before("@annotation(com.bezkoder.springjwt.annotation.Loggable)")
////    @Before("annotation(Pointcut.class)")
//    public void logAllMethodCallsAdvice(){
//        System.out.println("***********************************************************In Aspect ");
//    }
}

//@Aspect
//@Component
//public class AuditAspect {
//
//    @Autowired
//    private UserAuditRepository userAuditRepository; // Assume you have a UserAudit repository
//
//    @Pointcut("execution(public String com.bezkoder.springjwt.controllers.AuthController.*(..))")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object[] args = joinPoint.getArgs();
//        String methodName = joinPoint.getSignature().getName();
//
//        boolean actionResult = true; // Assume success initially
//        String actionDetails = joinPoint.getSignature().toShortString();
//        String oldValue = ""; // Set appropriate values if needed
//        String newValue = ""; // Set appropriate values if needed
//        String reasonForAction = ""; // Set appropriate values if needed
//
//        Object result;
//        try {
//            result = joinPoint.proceed(); // Proceed with the method execution
//        } catch (Throwable throwable) {
//            actionResult = false; // If an exception occurs, mark as failure
//            throw throwable; // Rethrow the exception
//        } finally {
//            // Create and save audit record
//            UserAudit userAudit = new UserAudit();
//            userAudit.setActionTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//            userAudit.setActionResult(actionResult);
//            userAudit.setActionDetails(actionDetails);
//            userAudit.setOldValue(oldValue);
//            userAudit.setNewValue(newValue);
//            userAudit.setReasonForAction(reasonForAction);
//            // You might want to fetch user details here based on the context
//            // e.g., from SecurityContextHolder
//            userAuditRepository.save(userAudit);
//        }
//        return result;
//    }
//}
//    @Around("execution(* com.bezkoder.springjwt.*.*(..))")
//    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object result;
//        boolean actionResult = true; // Assume success initially
//        String actionDetails = joinPoint.getSignature().toShortString();
//        String oldValue = ""; // Set appropriate values if needed
//        String newValue = ""; // Set appropriate values if needed
//        String reasonForAction = ""; // Set appropriate values if needed
//
//        try {
//            result = joinPoint.proceed(); // Proceed with the method execution
//        } catch (Throwable throwable) {
//            actionResult = false; // If an exception occurs, mark as failure
//            throw throwable; // Rethrow the exception
//        } finally {
//            // Create and save audit record
//            UserAudit userAudit = new UserAudit();
//            userAudit.setActionTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//            userAudit.setActionResult(actionResult);
//            userAudit.setActionDetails(actionDetails);
//            userAudit.setOldValue(oldValue);
//            userAudit.setNewValue(newValue);
//            userAudit.setReasonForAction(reasonForAction);
//            // You might want to fetch user details here based on the context
//            // e.g., from SecurityContextHolder
//            userAuditRepository.save(userAudit);
//        }
//        return result;
//    }
//}