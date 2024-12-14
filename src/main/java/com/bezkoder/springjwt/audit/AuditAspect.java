//package com.bezkoder.springjwt.audit;
//
//import com.bezkoder.springjwt.config.AuthTokenFilter;
//import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
//import org.apache.juli.logging.LogFactory;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@Aspect
//public class AuditAspect {
//    @Autowired
//    AuthTokenFilter authTokenFilter;
//    @Autowired
//    UserDetailsServiceImpl userDetailsServiceImpl;
//
//    private static Logger logger = LoggerFactory.getLogger(AuditAspect.class.getName());
//
//    @Before("@annotation(com.bezkoder.springjwt.annotation.Loggable)")
//    public void logPointcut() {
//        userDetailsServiceImpl.toString();
//        authTokenFilter.toString();
//        SecurityContext context = SecurityContextHolder.getContext();
//        System.out.println(context.toString() + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//    }
//
//}
//
//
////    @Before("execution(* org.springframework.security.web.FilterChainProxy.*(..))")
////        public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
////            // start stopwatch
////            Object retVal = pjp.proceed();
////            // stop stopwatch
////        System.out.println("&&&&&&&&%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
////            return retVal;
////        }
//
////    @After("execution(* org.springframework.security.web.context.SecurityContextHolderFilter.*(..))")
////    public Object logAroundSecurity(JoinPoint jp) throws Throwable {
////        // start stopwatch
////        Object retVal = jp.toString();
////        // stop stopwatch
////        System.out.println("&&&&&&&&%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
////        return retVal;
////    }
////    @Before("@annotation(com.bezkoder.springjwt.annotation.Loggable)")
//////    @Before("annotation(Pointcut.class)")
////    public void logAllMethodCallsAdvice(){
////        System.out.println("***********************************************************In Aspect ");
////    }
////        @After("execution(* org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.*(..))")
//////      @Before("execution(* com.xyz.dao.*.*(..))")
////        public void ffffff() {
////            System.out.println("222222&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
////
//////            userDetailsServiceImpl.loadUserByUsername();
////            authTokenFilter.toString();
////            SecurityContext context = SecurityContextHolder.getContext();
////            System.out.println(context.toString() + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
////        }
//
//
////        @After("execution(* org.springframework.security.authentication.AuthenticationManager.*(..))")
////        public void afterFilterExecution() {
////            authTokenFilter.toString();
////            SecurityContext context = SecurityContextHolder.getContext();
////            // Extract information from context (e.g., Authentication)
////            Authentication authentication = context.getAuthentication();
////            if (authentication != null) {
////                System.out.println("Extracted username:  ****************** " + authentication.getName());
////            } else {
////                System.out.println("No authentication found O)()I)I)UI)I)IU)I)");
////            }
////        }