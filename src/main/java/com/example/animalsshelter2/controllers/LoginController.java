package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.models.views.LoginViewModel;
import com.example.animalsshelter2.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;
    private final AuthenticationManager authentication;

    public LoginController(UserService userService, AuthenticationManager authentication) {
        this.userService = userService;
        this.authentication = authentication;
    }


    @GetMapping()
    public ResponseEntity<HttpStatus> userLogin(@RequestBody LoginViewModel loginServiceModel)throws Exception{
        Authentication authObj;
        try{
            authObj=authentication.authenticate(new UsernamePasswordAuthenticationToken(loginServiceModel.getEmail(),loginServiceModel.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authObj);
        }catch (BadCredentialsException e){
            throw new Exception("Invalid");
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

}
