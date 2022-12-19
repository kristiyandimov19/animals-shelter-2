package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.Comment;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.services.RegisterServiceModel;
import com.example.animalsshelter2.models.views.UserAvailableViewModel;
import com.example.animalsshelter2.models.views.UserIdViewModel;

import java.util.List;

public interface UserService {

    User findByName(String name);

    User findUserById(Long id);

    UserIdViewModel findById(Long id);

//    void seedUsers();

    List<UserAvailableViewModel> findAllAvailable();

    List<UserAvailableViewModel> findAllUsers();

    void takeOnWalk(Long userId, Long animalId);

    void returnFromWalk(Long userId, Long animalId);

    void addComment(Long adminId, Long userId, String description);

    void save(RegisterServiceModel newUser);
}
