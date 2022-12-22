package com.example.animalsshelter2.mockito;

import com.example.animalsshelter2.models.*;
import com.example.animalsshelter2.models.enums.UserRoleEnum;

import java.time.LocalDate;
import java.util.List;

public class UtilsMock {

    public static User createMockUser(){
        var mockUser=new User();
        mockUser.setId(17L);
        mockUser.setRole(createMockRole("ADMIN"));
        mockUser.setAnimal(createMockAnimal());
        mockUser.setComments(List.of(createComment()));
        mockUser.setPassword("password");
        mockUser.setEmail("zx");
        mockUser.setUsername("zx");

        return mockUser;
    }

    public static Animal createMockAnimal(){
        var mockAnimal=new Animal();
        mockAnimal.setId(1L);
        mockAnimal.setName("Rex");
        mockAnimal.setType("Dog");

        return mockAnimal;

    }

    public static UserRole createMockRole(String role){
        var mockUserRole=new UserRole();
        mockUserRole.setId(1L);
        mockUserRole.setRole(UserRoleEnum.ADMIN);

        return mockUserRole;
    }


    public static WalkHistory createMockWalkHistory(){
        var mockWalkHistory=new WalkHistory();
        mockWalkHistory.setId(1L);
        mockWalkHistory.setAnimalName("REX");
        mockWalkHistory.setUserId(17L);
        mockWalkHistory.setLocalDate(LocalDate.now());

        return  mockWalkHistory;

    }

    public static Comment createComment(){
            var createMockComment=new Comment();
            createMockComment.setId(1L);
            createMockComment.setAuthor("Ivan");
            createMockComment.setDescription("alallalal");

            return  createMockComment;
    }

}
