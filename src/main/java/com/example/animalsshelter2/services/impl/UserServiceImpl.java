package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public void seedUsers() {
        User user1 = new User()
                .setUsername("Admin")
                .setEmail("admin@admin.bg")
                .setPassword("asdasd")
                .setAdmin(true);

        User user2 = new User()
                .setUsername("User")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        userRepository.saveAll(List.of(user1, user2));
    }
}
