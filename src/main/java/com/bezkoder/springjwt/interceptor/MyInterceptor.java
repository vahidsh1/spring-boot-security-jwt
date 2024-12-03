package com.bezkoder.springjwt.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class MyCustomInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
//unimplemented methods comes here. Define the following method so that it
    //will handle the request before it is passed to the controller.

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response){
        //your custom logic here.
        return true;
    }
}