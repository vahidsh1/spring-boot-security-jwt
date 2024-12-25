package com.bezkoder.springjwt.exception;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AopExceptionHandler {
    @AfterThrowing(
            pointcut="execution(* com.bezkoder.springjwt..*(..))",throwing="ex")
    public void doRecoveryActions(Exception ex) {
        System.out.println("klklklllllllkkkkkkkkkkkkk");
    }
}
