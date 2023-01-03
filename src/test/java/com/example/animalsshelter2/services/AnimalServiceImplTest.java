package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.UserRole;
import com.example.animalsshelter2.models.enums.UserRoleEnum;
import com.example.animalsshelter2.models.response.AnimalAvailableResponse;
import com.example.animalsshelter2.models.request.AnimalRequest;
import com.example.animalsshelter2.models.response.AnimalResponse;
import com.example.animalsshelter2.models.response.AnimalWalkResponse;
import com.example.animalsshelter2.repositories.AnimalRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.services.impl.AnimalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private AnimalResponse animalResponse1, animalResponse2;
    private User user1 = new User();
    private UserRole admin = new UserRole().setRole(UserRoleEnum.ADMIN);
    private UserRole userRole = new UserRole().setRole(UserRoleEnum.USER);
    private AnimalRequest animalRequest;
    private PageRequest pageRequest;
    private Page<Animal> animalPage;
    private Page<Animal> animalPageable;
    private Page<Animal> animalPageableOnWalk;

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

        animalResponse1 = new AnimalResponse();
        animalResponse1.setId(1L);
        animalResponse1.setName("Jordan");
        animalResponse1.setType("Kuchka");
        animalResponse1.setAvailability(true);

        animalResponse2 = new AnimalResponse();
        animalResponse2.setId(2L);
        animalResponse2.setName("Martincho");
        animalResponse2.setType("tiger");
        animalResponse2.setAvailability(false);

        animalRequest = new AnimalRequest();
        animalRequest.setId(3L);
        animalRequest.setName("Dadada");
        animalRequest.setType("riba");
        animalRequest.setAvailability(true);

        animalService = new AnimalServiceImpl(animalRepository, new ModelMapper(), userRepository);
        pageRequest = PageRequest.of(1, 6);
        animalPage = new PageImpl<>(List.of(animal1), pageRequest, 10);
        animalPageable = animalPage;
        animalPageableOnWalk = new PageImpl<>(List.of(animal2), pageRequest, 10);
    }

    @Test
    void findAll() {
        when(animalRepository.findAll(pageRequest))
                .thenReturn(animalPageable);

        Page<AnimalResponse> result = animalService.findAll(1);

        assertEquals(animalPageable.getSize(), result.getSize());
    }

    @Test
    void createAnimal() {
        animalService.createAnimal(animalRequest);

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

        AnimalAvailableResponse actualResult = animalService.findAnimalById(1L);


        assertEquals(animal1.getId(), actualResult.getId());
        verify(animalRepository).findById(anyLong());
    }

    @Test
    void findAllAvailable() {
        when(animalRepository.findAllAvailable(pageRequest))
                .thenReturn(animalPageableOnWalk);

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));

        Page<AnimalWalkResponse> actualResult = animalService.findAllAvailable(1);

        assertEquals(animalPageableOnWalk.getSize(), actualResult.getSize());
        assertEquals(animalPageableOnWalk.getContent().get(0).getId(), actualResult.getContent().get(0).getId());
        verify(animalRepository).findAllAvailable(any(PageRequest.class));
    }

    @Test
    void adoptTest() {
        animalService.adoptAnimal(1L);

        verify(animalRepository).deleteById(1L);
    }
}