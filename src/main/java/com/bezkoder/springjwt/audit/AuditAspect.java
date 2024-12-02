package com.bezkoder.springjwt.audit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AuditAspect {

    //    @Pointcut("@annotation(Log)")
    @Before("execution(* com.bezkoder.springjwt.controllers.*.*(..))")
//    @Before("execution(* com.xyz.dao.*.*(..))")
    public void logPointcut(){
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
    }
    @Before("execution(* org.springframework.security.web.FilterChainProxy.*(..))")
        public void logAround()  {


        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
    }
    @Before("@annotation(com.bezkoder.springjwt.annotation.Loggable)")
//    @Before("annotation(Pointcut.class)")
    public void logAllMethodCallsAdvice(){
        System.out.println("***********************************************************In Aspect ");
    }
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