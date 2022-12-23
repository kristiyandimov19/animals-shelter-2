package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.request.RegisterRequest;
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

    void addComment(Long adminId, Long userId, String description);

    void save(RegisterRequest newUser);
}
