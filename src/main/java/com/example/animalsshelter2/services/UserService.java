package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.views.UserAvailableViewModel;

import java.util.List;

public interface UserService {

    User findByName(String name);

    void seedUsers();

    List<UserAvailableViewModel> findAllAvailable();

    List<UserAvailableViewModel> findAllUsers();
}
