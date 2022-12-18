//package com.example.animalsshelter2.config;
//
//import com.example.animalsshelter2.services.AnimalService;
//import com.example.animalsshelter2.services.UserService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DBInit implements CommandLineRunner {
//
//    private final AnimalService animalService;
//    private final UserService userService;
//
//    public DBInit(AnimalService animalService, UserService userService) {
//        this.animalService = animalService;
//        this.userService = userService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        animalService.seedAnimals();
//
//        userService.seedUsers();
//    }
//}
