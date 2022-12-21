//package com.example.animalsshelter2.controllers;
//
//import com.example.animalsshelter2.models.services.RegisterServiceModel;
//import com.example.animalsshelter2.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/reg")
//public class RegistrationController {
//
//
//    private final UserService userService;
//
//    public RegistrationController(UserService userService) {
//
//        this.userService = userService;
//    }
//
//    @PostMapping()
//    public void reg(@RequestBody RegisterServiceModel registerServiceModel){
//        userService.save(registerServiceModel);
//    }
//
//
//
//
//
//
//
//
//}
