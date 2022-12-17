//package com.example.animalsshelter2.controllers;
//
//import com.example.animalsshelter2.exceptions.AuthenticationFailureException;
//import com.example.animalsshelter2.models.User;
//import com.example.animalsshelter2.services.UserService;
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ResponseStatusException;
//
//@Component
//public class Authentication {
//    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
//    public static final String AUTHENTICATION_FAILURE_MESSAGE = "Wrong email or password.";
//
//    private final BCryptPasswordEncoder passwordEncoder;
//    private final UserService userService;
//
//
//    @Autowired
//    public Authentication(UserService userService) {
//        this.userService = userService;
//        this.passwordEncoder = new BCryptPasswordEncoder();
//    }
//
//    public User tryGetUser(HttpHeaders headers) {
//        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The requested resource requires authentication.");
//        }
//
//        try {
//            String email = headers.getFirst(AUTHORIZATION_HEADER_NAME);
//            return userService.findByEmail(email);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email username.");
//        }
//    }
//
//    public User tryGetUser(HttpSession session) {
//        String currentUser = (String) session.getAttribute("currentUser");
//        if (currentUser == null) {
//            throw new AuthenticationFailureException("No user logged in.");
//        }
//        return userService.findByName(currentUser);
//    }
//
//
//
//    public User verifyAuthentication(String email, String password) {
//        try {
//            User user = userService.findByName(email);
//            if (!passwordEncoder.matches(password, user.getPassword())) {
//                throw new AuthenticationFailureException(AUTHENTICATION_FAILURE_MESSAGE);
//            }
//            return user;
//        } catch (EntityNotFoundException e) {
//            throw new AuthenticationFailureException(AUTHENTICATION_FAILURE_MESSAGE);
//        }
//    }
//
//
//
//}
//
