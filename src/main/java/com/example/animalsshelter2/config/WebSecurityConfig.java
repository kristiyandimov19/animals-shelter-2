package com.example.animalsshelter2.config;

import com.example.animalsshelter2.models.CustomUserDetails;
import com.example.animalsshelter2.models.services.UserAuthServiceModel;
import com.example.animalsshelter2.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CustomUserDetails customUserDetails;
    private final PasswordEncoder passwordEncoder;

    private final JwtAuthFilter jwtAuthFilter;
    private final UserRepository userRepository;

    public WebSecurityConfig(CustomUserDetails customUserDetails, PasswordEncoder passwordEncoder, JwtAuthFilter jwtAuthFilter,
                             UserRepository userRepository) {
        this.customUserDetails = customUserDetails;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthFilter = jwtAuthFilter;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http
                    .csrf()
                    .disable()
                    .authorizeHttpRequests()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers("/bootstrap/**").permitAll()
                    .requestMatchers("/",
                            "/index.html",
                            "/login.html",
                            "/register.html",
                            "/login",
                            "/register",
                            "/animal/all",
                            "/info")
                    .permitAll()
                    .requestMatchers("/users/**", "/user_history.html", "/history.html")
                    .hasRole("USER")
                    .requestMatchers( "/animal/**",
                            "/add_animal.html",
                            "/adopt.html",
                            "/return_from_a_walk.html",
                            "/take_on_a_walk.html",
                            "/walk.html")
                    .hasRole("ADMIN")
                    .and()
                    .userDetailsService(customUserDetails)
                    .exceptionHandling()
                    .authenticationEntryPoint(
                            (request, response, authException) ->
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                    )
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

            http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}