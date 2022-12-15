package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.views.UserAvailableViewModel;
import com.example.animalsshelter2.models.views.UserIdViewModel;

import java.util.List;

public interface UserService {

    User findByName(String name);

    UserIdViewModel findById(Long id);

    void seedUsers();

    List<UserAvailableViewModel> findAllAvailable();

    List<UserAvailableViewModel> findAllUsers();

    void takeOnWalk(Long userId, Long animalId);

    void returnFromWalk(Long userId, Long animalId);
}
