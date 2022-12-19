package com.example.animalsshelter2.config;
import com.example.animalsshelter2.models.services.RegisterServiceModel;
import com.example.animalsshelter2.services.impl.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig   {


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService)
//            throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder)
//                .and()
//                .build();
//    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception {

        return  http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/reg").permitAll();
                    auth.requestMatchers("/user").hasRole("user");
                    auth.requestMatchers("/admin").hasRole("admin");

        })
                .httpBasic()
                .and().build();

    }
//        http.authorizeHttpRequests()
//                .requestMatchers("/admin/**")
//
//
//                .hasAuthority("admin")
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

    }