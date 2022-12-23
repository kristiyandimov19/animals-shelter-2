package com.example.animalsshelter2.junit.impl;

import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.UserRole;
import com.example.animalsshelter2.models.WalkHistory;
import com.example.animalsshelter2.models.enums.UserRoleEnum;
import com.example.animalsshelter2.repositories.WalkHistoryRepository;
import com.example.animalsshelter2.services.impl.WalkHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalkHistoryServiceImplTest {

    @Mock
    private WalkHistoryRepository walkHistoryRepository;

    private WalkHistory walkHistory = new WalkHistory();

    private User user;

    private WalkHistoryServiceImpl walkHistoryService;

    @BeforeEach
    void setUp() {
        user = new User("Pesho", "test1@gmail.com", "password", new UserRole().setRole(UserRoleEnum.ADMIN), null);
        user.setId(1L);
        walkHistory.setId(1L);
        walkHistory.setAnimalName("Gosho")
                .setAnimalType("Dog")
                .setUser(user);
        walkHistory.setLocalDate(LocalDate.now());
        walkHistoryService = new WalkHistoryServiceImpl(walkHistoryRepository);

    }

    @Test
    void findByUserId() {
        when(walkHistoryRepository.findAllByUserId(any(), anyLong()))
                .thenReturn(List.of(walkHistory));

        List<WalkHistory> actualResult = walkHistoryService.findByUserId(1L);

        List<WalkHistory> expectedResult = List.of(walkHistory);

        assertEquals(expectedResult.get(0).getId(), actualResult.get(0).getId());
        verify(walkHistoryRepository).findAllByUserId(any(), anyLong());
    }
}