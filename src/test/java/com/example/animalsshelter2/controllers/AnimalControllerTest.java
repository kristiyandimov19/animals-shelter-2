package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.models.request.AnimalRequest;
import com.example.animalsshelter2.models.response.AnimalAvailableResponse;
import com.example.animalsshelter2.models.response.AnimalResponse;
import com.example.animalsshelter2.models.response.AnimalWalkResponse;
import com.example.animalsshelter2.services.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AnimalControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    Page<AnimalResponse> animalResponses;
    Page<AnimalWalkResponse> animalWalkResponses;

    AnimalResponse animalResponse;
    AnimalRequest animalRequest;
    AnimalWalkResponse animalWalkResponse;
    AnimalAvailableResponse animalAvailableResponse;

    @BeforeEach
    void setUp() {
        animalResponse = new AnimalResponse();
        animalResponse.setId(1L);
        animalResponse.setAvailability(true);
        animalResponse.setName("Gosho");
        animalResponse.setType("Dog");

        animalWalkResponse = new AnimalWalkResponse();
        animalWalkResponse.setUsername("User");
        animalWalkResponse.setId(1L);
        animalWalkResponse.setName("Animal");
        animalWalkResponse.setType("Dog");

        animalAvailableResponse = new AnimalAvailableResponse();
        animalAvailableResponse.setAvailability(true);
        animalAvailableResponse.setId(1L);
        animalAvailableResponse.setUserId(1L);

        animalRequest = new AnimalRequest();
        animalRequest.setId(1L).setAvailability(true).setName("Animal").setType("Dog");

        animalWalkResponses = new PageImpl<>(List.of(animalWalkResponse), PageRequest.of(0, 10), 10);
        animalResponses = new PageImpl<>(List.of(animalResponse), PageRequest.of(0, 10), 10);
    }

    @Test
    @WithAnonymousUser
    void getAllAnimals() throws Exception {
        when(animalService.findAll(anyInt()))
                .thenReturn(animalResponses);

        mockMvc.perform(get("/animal/page/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "content": [
                                {
                                    "id": 1,
                                    "name": "Gosho",
                                    "type": "Dog",
                                    "availability": true
                                }
                            ],
                            "pageable": {
                                "sort": {
                                    "empty": true,
                                    "sorted": false,
                                    "unsorted": true
                                },
                                "offset": 0,
                                "pageSize": 10,
                                "pageNumber": 0,
                                "unpaged": false,
                                "paged": true
                            },
                            "last": true,
                            "totalPages": 1,
                            "totalElements": 10,
                            "size": 10,
                            "number": 0,
                            "sort": {
                                "empty": true,
                                "sorted": false,
                                "unsorted": true
                            },
                            "first": true,
                            "numberOfElements": 1,
                            "empty": false
                        }
                        """
                ));
        verify(animalService).findAll(anyInt());
    }

    @Test
    void getVolunteerFor() {
    }

//    @Test
//    void createAnimal() {
//        mockMvc.perform(post("/animal"))
//
//    }

    @Test
    @WithMockUser(username = "admin1@admin.bg", authorities = "ADMIN")
    void isAvailable() throws Exception {
        when(animalService.findAnimalById(anyLong()))
                .thenReturn(animalAvailableResponse);

        mockMvc.perform(get("/animal/isAvailable/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "id": 1,
                            "availability": true,
                            "userId": 1
                        }
                        """));

        verify(animalService).findAnimalById(anyLong());
    }

    @Test
    @WithMockUser(username = "admin1@admin.bg", authorities = "ADMIN")
    void onWalk() throws Exception {
        when(animalService.findAllAvailable(anyInt()))
                .thenReturn(animalWalkResponses);

        mockMvc.perform(get("/animal/onWalk/page/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "content": [
                                {
                                    "id": 1,
                                    "name": "Animal",
                                    "type": "Dog",
                                    "username": "User"
                                }
                            ],
                            "pageable": {
                                "sort": {
                                    "empty": true,
                                    "sorted": false,
                                    "unsorted": true
                                },
                                "offset": 0,
                                "pageSize": 10,
                                "pageNumber": 0,
                                "unpaged": false,
                                "paged": true
                            },
                            "last": true,
                            "totalPages": 1,
                            "totalElements": 10,
                            "size": 10,
                            "number": 0,
                            "sort": {
                                "empty": true,
                                "sorted": false,
                                "unsorted": true
                            },
                            "first": true,
                            "numberOfElements": 1,
                            "empty": false
                        }
                        """
                ));
        verify(animalService).findAllAvailable(anyInt());
    }

    @Test
    @WithMockUser(username = "admin1@admin.bg", authorities = "ADMIN")
    void adoptAnimal() throws Exception {
        mockMvc.perform(delete("/animal/adopt/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(animalService).adoptAnimal(1L);
    }

}