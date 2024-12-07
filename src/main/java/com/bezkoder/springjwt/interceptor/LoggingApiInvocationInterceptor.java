//package com.bezkoder.springjwt.interceptor;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//
//public class LoggingApiInvocationInterceptor implements HandlerInterceptor {
//
//    private static Logger log = LoggerFactory.getLogger(LoggingApiInvocationInterceptor.class);
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info("[************preHandle][" + request + "]" + "[" + request.getMethod()
//                + "]" + request.getRequestURI() + request.getServletPath());
//
//    }
////unimplemented methods comes here. Define the following method so that it
//    //will handle the request before it is passed to the controller.
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.info("postHandle]"+request.getServletPath()
//                + " 1 *** " + request.getRequestURI()
//                + " 2 *** " + request.getMethod()
//                + " 3 *** " + request.getLocalAddr()
//                + " 4 *** " + request.getCookies().toString()
//                + " 5 *** " + request.getRequestedSessionId()
//                + " 6 *** " + request.getServerName()
//                + " 7 *** " + request.getServerPort()
//                + " 8 *** " + request.getSession().getId()
//                + " 9 *** " + request.getSession().getServletContext().getInitParameter("session")
//                + " 10 *** " + request.getSession().getServletContext().getServerInfo()
//
//        );
//
//        return true;
//    }
//}