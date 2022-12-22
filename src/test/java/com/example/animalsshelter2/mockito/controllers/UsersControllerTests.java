package com.example.animalsshelter2.mockito.controllers;

import com.example.animalsshelter2.controllers.UserController;
import com.example.animalsshelter2.services.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@ExtendWith(MockitoExtension.class)
public class UsersControllerTests {

    @InjectMocks
    UserController mockUserController;

    @Mock
    UserService userService;

}
