package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.config.JwtUtils;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.request.LoginRequest;
import com.example.animalsshelter2.models.request.RegisterRequest;
import com.example.animalsshelter2.models.response.LoginResponse;
import com.example.animalsshelter2.models.response.RegistrationResponse;
import com.example.animalsshelter2.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Map;

@RestController
public class AdmissionController {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    public AdmissionController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public RegistrationResponse register(@RequestBody RegisterRequest registerRequest) {
        return userService.save(registerRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

}
