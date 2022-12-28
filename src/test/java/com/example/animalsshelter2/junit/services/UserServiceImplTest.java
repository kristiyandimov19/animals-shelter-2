package com.example.animalsshelter2.junit.services;

import com.example.animalsshelter2.config.JwtUtils;
import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.UserRole;
import com.example.animalsshelter2.models.enums.UserRoleEnum;
import com.example.animalsshelter2.models.response.UserAvailableResponse;
import com.example.animalsshelter2.models.response.UserIdResponse;
import com.example.animalsshelter2.repositories.AnimalRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.repositories.UserRoleRepository;
import com.example.animalsshelter2.repositories.WalkHistoryRepository;
import com.example.animalsshelter2.services.CommentService;
import com.example.animalsshelter2.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private CommentService commentService;
    @Mock
    private WalkHistoryRepository walkHistoryRepository;
    @Mock
    private UserRoleRepository userRoleRepository;

    private UserServiceImpl userService;

    private JwtUtils jwtUtils;

    private AuthenticationManager authenticationManager;

    private ModelMapper modelMapper;
    private User user1, user2, user3;
    private UserRole admin = new UserRole().setRole(UserRoleEnum.ADMIN);
    private UserRole userRole = new UserRole().setRole(UserRoleEnum.USER);

    private Animal animal1, animal2;

    @BeforeEach
    void setUp() {
        animal1 = new Animal();
        animal1.setId(1L);
        animal1.setName("Tosho");
        animal1.setType("tiger");
        animal1.setAvailability(true);

        animal2 = new Animal();
        animal2.setId(1L);
        animal2.setName("Azis");
        animal2.setType("pevec");
        animal2.setAvailability(false);

        user1 = new User("Pesho", "test1@gmail.com", "password", userRole, null);
        user1.setId(1L);

        animal1.setUser(user1);

        user2 = new User("Gogo", "test2@gmail.com", "password", userRole, animal2);
        user2.setId(2L);

        user3 = new User("admin", "test3@gmail.com", "password", admin, null);
        user3.setId(3L);

        modelMapper = new ModelMapper();

        userService = new UserServiceImpl(userRepository, new ModelMapper(),
                animalRepository, commentService, walkHistoryRepository, new BCryptPasswordEncoder(), userRoleRepository, jwtUtils, authenticationManager);
    }

    @Test
    void findByName() {
        when(userRepository.findByUsername(anyString()))
                .thenReturn(user1);

        User actualResult = userService.findByName("Pesho");

        assertEquals(user1, actualResult);
        verify(userRepository).findByUsername(anyString());
    }

    @Test
    void findUserById() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));

        User actualResult = userService.findUserById(1L);

        assertEquals(user1, actualResult);
        verify(userRepository).findById(anyLong());
    }

    @Test
    void findById() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));

        UserIdResponse actualResult = userService.findById(1L);

        assertEquals(user1.getId(), actualResult.getId());
        verify(userRepository).findById(anyLong());
    }

    @Test
    void findAllByAnimalIsNullAndRole() {
        when(userRepository.findAllByAnimalIsNullAndRole(any()))
                .thenReturn(List.of(user2));

        List<UserAvailableResponse> actualResult = userService.findAllAvailable();

        assertEquals(user2.getId(), actualResult.get(0).getId());
        assertEquals(user2.getUsername(), actualResult.get(0).getUsername());
        verify(userRepository).findAllByAnimalIsNullAndRole(any());
    }

    @Test
    void findAllByRole() {
        when(userRepository.findAllByRole(any()))
                .thenReturn(List.of(user1, user2));

        List<UserAvailableResponse> actualResult = userService.findAllUsers();

        List<UserAvailableResponse> expectedResult = List.of(user1, user2).stream()
                .map(user -> modelMapper.map(user, UserAvailableResponse.class))
                .toList();

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult.get(0).getId(), actualResult.get(0).getId());
        assertEquals(expectedResult.get(1).getId(), actualResult.get(1).getId());
        verify(userRepository).findAllByRole(any());
    }

    @Test
    void testTakeOnWalkOccupied() {
        assertThrows(Exception.class, () -> userService.takeOnWalk(user2.getId(), animal2.getId()));
    }

    @Test
    void takeOnWalk() throws Exception {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));

        when(animalRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(animal1));

        userService.takeOnWalk(1L, 1L);

        verify(userRepository, atLeast(2)).findById(anyLong());
        verify(userRepository).save(any());
        verify(animalRepository, atLeast(2)).findById(anyLong());
        verify(animalRepository).save(any());
    }

    @Test
    void returnFromWalk() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user2));

        when(animalRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(animal2));

        userService.returnFromWalk(user2.getId(), animal2.getId());

        verify(animalRepository).save(any());
        verify(userRepository).save(any());
        verify(walkHistoryRepository).save(any());
    }
}