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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
                    .httpBasic()
                    .disable()
                    .cors()
                    .and()
                    .authorizeHttpRequests()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers("/",
                            "/html/**",
                            "/login",
                            "/register",
                            "/animal/all",
                            "/info")
                    .permitAll()
                    .requestMatchers( "/users/walks/**")
                    .hasAnyAuthority("USER","ADMIN")
                    .requestMatchers( "/users/available",
                            "/users/all",
                            "/users/comments/**",
                            "/users/comment/add",
                            "/users/walks/**",
                            "/users/returnFromWalk/**",
                            "/users/takeOnWalk/**",
                            "/animal/**",
                            "/animal/delete/**")
                    .hasAuthority("ADMIN")
                    .requestMatchers("/bootstrap/**").permitAll()
                    .and()
                    .userDetailsService(customUserDetails)
                    .exceptionHandling()
                    .authenticationEntryPoint(
                            (request, response, authException) ->
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                    )
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}