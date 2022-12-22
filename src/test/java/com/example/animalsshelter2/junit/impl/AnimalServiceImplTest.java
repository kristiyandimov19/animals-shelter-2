package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.UserRole;
import com.example.animalsshelter2.models.enums.UserRoleEnum;
import com.example.animalsshelter2.models.services.AnimalAvailableServiceModel;
import com.example.animalsshelter2.models.services.AnimalServiceModel;
import com.example.animalsshelter2.models.views.AnimalViewModel;
import com.example.animalsshelter2.models.views.AnimalWalkViewModel;
import com.example.animalsshelter2.repositories.AnimalRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceImplTest {

    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private UserRepository userRepository;

    private AnimalServiceImpl animalService;

    private Animal animal1, animal2;
    private AnimalViewModel animalViewModel1, animalViewModel2;
    private User user1 = new User();
    private UserRole admin = new UserRole().setRole(UserRoleEnum.ADMIN);
    private UserRole userRole = new UserRole().setRole(UserRoleEnum.USER);
    private AnimalServiceModel animalServiceModel;

    @BeforeEach
    void setUp() {
        user1.setId(1L);
        user1.setEmail("test@gmail.com");
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setRole(userRole);

        animal1 = new Animal();
        animal1.setId(1L);
        animal1.setName("Jordan");
        animal1.setType("Kuchka");
        animal1.setAvailability(true);
        animal1.setUser(null);

        animal2 = new Animal();
        animal2.setId(2L);
        animal2.setName("Martincho");
        animal2.setType("tiger");
        animal2.setAvailability(false);
        animal2.setUser(user1);

        animalViewModel1 = new AnimalViewModel();
        animalViewModel1.setId(1L);
        animalViewModel1.setName("Jordan");
        animalViewModel1.setType("Kuchka");
        animalViewModel1.setAvailability(true);

        animalViewModel2 = new AnimalViewModel();
        animalViewModel2.setId(2L);
        animalViewModel2.setName("Martincho");
        animalViewModel2.setType("tiger");
        animalViewModel2.setAvailability(false);

        animalServiceModel = new AnimalServiceModel();
        animalServiceModel.setId(3L);
        animalServiceModel.setName("Dadada");
        animalServiceModel.setType("riba");
        animalServiceModel.setAvailability(true);

        animalService = new AnimalServiceImpl(animalRepository, new ModelMapper(), userRepository);
    }

    @Test
    void findAll() {
        when(animalRepository.findAll())
                .thenReturn(List.of(animal1, animal2));

        List<AnimalViewModel> actualResult = animalService.findAll();

        List<AnimalViewModel> expectedResult = List.of(animalViewModel1, animalViewModel2);

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult.get(1).getId(), actualResult.get(1).getId());
        verify(animalRepository).findAll();
    }

    @Test
    void createAnimal() {
        animalService.createAnimal(animalServiceModel);

        verify(animalRepository).save(any());
    }

    @Test
    void adoptAnimal() {
        animalRepository.deleteById(1L);
        verify(animalRepository).deleteById(anyLong());
    }

    @Test
    void findAnimalById() {
        when(animalRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(animal1));

        AnimalAvailableServiceModel actualResult = animalService.findAnimalById(1L);


        assertEquals(animal1.getId(), actualResult.getId());
        verify(animalRepository).findById(anyLong());
    }

    @Test
    void findAllAvailable() {
        when(animalRepository.findAllAvailable())
                .thenReturn(List.of(animal2));

        List<AnimalWalkViewModel> actualResult = animalService.findAllAvailable();

        List<Animal> expectedResult = List.of(animal2);

        assertEquals(expectedResult.get(0).getId(), actualResult.get(0).getId());
        verify(animalRepository).findAllAvailable();
    }
}