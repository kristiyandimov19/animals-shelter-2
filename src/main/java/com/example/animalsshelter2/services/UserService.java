package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.request.LoginRequest;
import com.example.animalsshelter2.models.request.RegisterRequest;
import com.example.animalsshelter2.models.response.LoginResponse;
import com.example.animalsshelter2.models.response.RegistrationResponse;
import com.example.animalsshelter2.models.response.UserAvailableResponse;
import com.example.animalsshelter2.models.response.UserIdResponse;

import java.util.List;

public interface UserService {

    User findByName(String name);

    User findByEmail(String email);

    User findUserById(Long id);

    UserIdResponse findById(Long id);

    void seedUsers();

    List<UserAvailableResponse> findAllAvailable();

    List<UserAvailableResponse> findAllUsers();

    void takeOnWalk(Long userId, Long animalId) throws Exception;

    void returnFromWalk(Long userId, Long animalId);

    RegistrationResponse save(RegisterRequest newUser);

    LoginResponse login(LoginRequest loginRequest);

    boolean isTokenExpired(String token);
}
