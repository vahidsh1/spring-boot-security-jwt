package com.bezkoder.springjwt.config;

import com.bezkoder.springjwt.exception.GlobalExceptionHandler;
import com.bezkoder.springjwt.filter.ApiLoggingFilter;

import com.bezkoder.springjwt.filter.GlobalExceptionHandlerFilter;
import com.bezkoder.springjwt.handler.CustomAccessDeniedHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bezkoder.springjwt.security.jwt.AuthEntryPointJwt;
import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity

// (securedEnabled = true,
// jsr250Enabled = true,
// prePostEnabled = true) // by default
public class WebSecurityConfig {//extends WebSecurityConfigurerAdapter  {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private ApiLoggingFilter apiLoggingFilter;  // Let Spring manage the filter
    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/favicon.ico", "/css/**", "/js/**", "/error/**", "/error")
                .requestMatchers("/h2-console/**");
    }

    //    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .exceptionHandling(exception -> {
//                    exception.authenticationEntryPoint(authEntryPointJwt);
//                    exception.accessDeniedHandler(accessDeniedHandler());
//                })
//                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(
//                        auth -> auth.requestMatchers("/api/auth/login").permitAll()
//                                .requestMatchers("/api/auth/signup").hasRole("ADMIN")
//                                .requestMatchers("/h2-console/**", "/favicon.ico", "/error").permitAll()  // Allow access to H2 and error pages
//                                .anyRequest().authenticated()
//                )
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(apiLoggingFilter, SessionManagementFilter.class)
//
////                .addFilterAfter(apiLoggingFilter(), AuthorizationFilter.class)
//        ;
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/auth/signup").hasRole("ADMIN")
                        .requestMatchers("/h2-console/**", "/favicon.ico").permitAll()  // Allow access to H2 and error pages
                        .anyRequest().authenticated()
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(apiLoggingFilter, UsernamePasswordAuthenticationFilter.class);  // Logging filter
        http.addFilterBefore(new GlobalExceptionHandlerFilter(), AuthorizationFilter.class);  // Logging filter

        return http.build();
    }
}
