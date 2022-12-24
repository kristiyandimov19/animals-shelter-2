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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

    private Page<WalkHistory> walkHistoryPage;

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
        PageRequest requestPage = PageRequest.of(1, 5, Sort.by(Sort.Direction.DESC, "id"));
        walkHistoryPage = new PageImpl<>(List.of(walkHistory), requestPage, 5);

    }

    @Test
    void findByUserId() {
        Page<WalkHistory> expectedResult = walkHistoryPage;
        when(walkHistoryRepository.findAllByUserId(any(LocalDate.class), any(Long.class), any(PageRequest.class)))
                .thenReturn(expectedResult);

        Page<WalkHistory> result = walkHistoryService.findByUserId(1L, 0);

        assertEquals(expectedResult, result);
    }
}