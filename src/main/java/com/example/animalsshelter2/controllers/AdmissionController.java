package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.models.request.LoginRequest;
import com.example.animalsshelter2.models.request.RegisterRequest;
import com.example.animalsshelter2.models.response.LoginResponse;
import com.example.animalsshelter2.models.response.RegistrationResponse;
import com.example.animalsshelter2.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdmissionController {
    private final UserService userService;


    public AdmissionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public RegistrationResponse register(@RequestBody RegisterRequest registerRequest) {
        return userService.save(registerRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping("/check")
    public boolean checkValidToken(@RequestHeader("Authorization") String token) {
        return userService.isTokenExpired(token.replace("Bearer ", ""));
    }

}
