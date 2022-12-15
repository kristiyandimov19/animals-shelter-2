package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.models.views.UserAvailableViewModel;
import com.example.animalsshelter2.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/available")
    public List<UserAvailableViewModel> allAvailable() {
        return userService.findAllAvailable();
    }

    @GetMapping("/all")
    public List<UserAvailableViewModel> findAllUsers() {
        return userService.findAllUsers();
    }

    @PutMapping("/takeOnWalk/{userId}/{animalId}")
    public void takeOnWalk(@PathVariable Long userId, @PathVariable Long animalId) {
        userService.takeOnWalk(userId, animalId);
    }
}
