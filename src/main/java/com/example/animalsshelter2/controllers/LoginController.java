package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.config.JwtUtils;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.services.LoginServiceModel;
import com.example.animalsshelter2.models.services.RegisterServiceModel;
import com.example.animalsshelter2.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping()
public class LoginController {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public LoginController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/info")
    public Collection<? extends GrantedAuthority> getUserDetails() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
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

            //Tezi 2 reda sum pipal
            User user = userService.findByEmail(loginServiceModel.getEmail());
            String token = jwtUtils.generateToken(user.getRole(),user.getEmail(),user.getId());

            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Login Credentials",e);
        }
    }
}
