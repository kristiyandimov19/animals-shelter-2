package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.config.JwtUtils;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.services.LoginServiceModel;
import com.example.animalsshelter2.models.services.RegisterServiceModel;
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
@RequestMapping()
public class LoginRegisterController {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    public LoginRegisterController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterServiceModel registerServiceModel) {
        try {
            userService.save(registerServiceModel);
            User user = userService.findByEmail(registerServiceModel.getEmail());
            String token = jwtUtils.generateToken(user.getRole(),user.getEmail(),user.getId());

            return Collections.singletonMap("jwt-token", token);
        }catch (InvalidParameterException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write it right !!!",e);
        }
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginServiceModel loginServiceModel) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(loginServiceModel.getEmail(),
                            loginServiceModel.getPassword());

            authenticationManager.authenticate(authInputToken);

            User user = userService.findByEmail(loginServiceModel.getEmail());
            String token = jwtUtils.generateToken(user.getRole(),user.getEmail(),user.getId());

            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Login Credentials",e);
        }
    }
}
