//package com.example.animalsshelter2.controllers;
//
//import com.example.animalsshelter2.models.CustomUserDetails;
//import com.example.animalsshelter2.services.UserService;
//import com.example.animalsshelter2.services.impl.CustomUserDetailsService;
//import com.example.animalsshelter2.services.impl.UserServiceImpl;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.CachingUserDetailsService;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@Order(1)
//public class AdminConfigAdapter {
//    private  UserServiceImpl userService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
//                .requestMatchers("/admin/**")
//
//
//                .hasAuthority("ADMIN")
//
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/admin")
//                .usernameParameter("email")
//                .failureUrl("/loginAdmin?error=loginError")
//                .defaultSuccessUrl("/admin")
//
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/protectedLinks")
//                .deleteCookies("JSESSIONID")
//
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/403")
//
//                .and()
//                .csrf().disable();
//        return http.build();
//    }
//}
