package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;

public interface UserService {

    User findByName(String name);

    void seedUsers();
}
