//package com.bezkoder.springjwt.utils;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StopWatch;
//
//@Aspect
//@Component
//public class LoggingAspect
//{
////    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);
//
//    //AOP expression for which methods shall be intercepted
//    @Around("execution(* com.bezkoder.springjwt..*(..)))")
//    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
//    {
//        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
//
//        //Get intercepted method details
//        String className = methodSignature.getDeclaringType().getSimpleName();
//        String methodName = methodSignature.getName();
//
//        final StopWatch stopWatch = new StopWatch();
//
//        //Measure method execution time
//        stopWatch.start();
//        Object result = proceedingJoinPoint.proceed();
//        stopWatch.stop();
//
//        //Log method execution time
//        System.out.println("Method execution time $4444444444444444444444444444444444");
//        return result;
//    }
//}