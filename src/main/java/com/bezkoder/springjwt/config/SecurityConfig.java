//package com.bezkoder.springjwt.config;
//
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/auth/changepassword").permitAll() // Allow access to change password
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(new FirstLoginFilter(authenticationManager()), BasicAuthenticationFilter.class);
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // Your authentication logic
//    }
//}
