package com.example.animalsshelter2.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class UserConfigAdapter {


    @Bean
    public SecurityFilterChain filterChainAUser(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/user*")

                .hasRole("USER")

                .and()
                .formLogin()
                .loginPage("/loginUser")
                .loginProcessingUrl("/user_login")
                .failureUrl("/loginUser?error=loginError")
                .defaultSuccessUrl("/userPage")

                .and()
                .logout()
                .logoutUrl("/user_logout")
                .logoutSuccessUrl("/protectedLinks")
                .deleteCookies("JSESSIONID")

                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")

                .and()
                .csrf().disable();
        return http.build();
    }
}
