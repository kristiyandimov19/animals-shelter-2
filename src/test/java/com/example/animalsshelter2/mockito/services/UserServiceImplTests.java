package com.example.animalsshelter2.mockito.services;

import com.example.animalsshelter2.repositories.AnimalRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.services.impl.AnimalServiceImpl;
import com.example.animalsshelter2.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static com.example.animalsshelter2.mockito.UtilsMock.createMockUserAdmin;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    UserRepository mockUserRepository;
    @Mock
    ModelMapper modelMapper;

    @Mock
    AnimalRepository mockAnimalRepository;

    @Mock
    AnimalServiceImpl mockAnimalServiceImpl;

    @InjectMocks
    UserServiceImpl mockUserServiceImpl;


    @Test
    public void getById_should_returnUser() {
        // Arrange
        var mockUser = createMockUserAdmin();

        Mockito.when(mockUserRepository.findById(mockUser.getId()))
                .thenReturn(Optional.of(mockUser));
        // Act
       var result = mockUserRepository.findById(mockUser.getId());

        // Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(mockUser.getId(), result.get().getId()
                ));

    }






}
