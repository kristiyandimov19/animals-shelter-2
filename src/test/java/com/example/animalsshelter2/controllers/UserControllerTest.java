package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.models.request.CommentRequest;
import com.example.animalsshelter2.models.response.CommentResponse;
import com.example.animalsshelter2.models.response.UserAvailableResponse;
import com.example.animalsshelter2.services.CommentService;
import com.example.animalsshelter2.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CommentService commentService;

    private UserAvailableResponse userAvailableResponse;

    private CommentResponse commentResponse;
    private CommentRequest commentRequest;

    @BeforeEach
    void setUp() {
        userAvailableResponse = new UserAvailableResponse()
                .setId(1L)
                .setUsername("User");

        commentResponse = new CommentResponse()
                .setAuthor("Author")
                .setDescription("Description");

        commentRequest = new CommentRequest()
                .setUserId(2L)
                .setDescription("Description")
                .setAuthorId(1L);
    }

    @Test
    @WithMockUser(username = "admin1@admin.bg", authorities = "ADMIN")
    void allAvailable() throws Exception {
        when(userService.findAllAvailable())
                .thenReturn(List.of(userAvailableResponse));

        mockMvc.perform(get("/available"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                            {
                                "id": 1,
                                "username": "User"
                            }
                        ]
                        """));

        verify(userService).findAllAvailable();
    }

    @Test
    @WithMockUser(username = "admin1@admin.bg", authorities = "ADMIN")
    void findAllUsers() throws Exception {
        when(userService.findAllUsers())
                .thenReturn(List.of(userAvailableResponse));

        mockMvc.perform(get("/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                            {
                                "id": 1,
                                "username": "User"
                            }
                        ]
                        """));

        verify(userService).findAllUsers();
    }

    @Test
    @WithMockUser(username = "admin1@admin.bg", authorities = "ADMIN")
    void getUserComments() throws Exception {
        when(commentService.findAllByUserId(anyLong()))
                .thenReturn(List.of(commentResponse));

        mockMvc.perform(get("/1/comments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                            {
                                "author": "Author",
                                "description": "Description"
                            }
                        ]
                        """));

        verify(commentService).findAllByUserId(anyLong());
    }

    @Test
    @WithMockUser(username = "admin1@admin.bg", authorities = "ADMIN")
    void takeOnWalk() throws Exception {
        mockMvc.perform(put("/1/animal/1/walk"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userService).takeOnWalk(anyLong(), anyLong());
    }

    @Test
    @WithMockUser(username = "admin1@admin.bg", authorities = "ADMIN")
    void returnFromWalk() throws Exception {
        mockMvc.perform(put("/1/animal/1/return"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userService).returnFromWalk(anyLong(), anyLong());
    }

    @Test
    @WithMockUser(username = "admin1@admin.bg", authorities = "ADMIN")
    void addComment() throws Exception {
        mockMvc.perform(put("/comment")
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .content("""
                                {
                                    "authorId": 1,
                                    "description": "Description",
                                    "userId": 2
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk());

        verify(commentService).createComment(commentRequest.getAuthorId(),
                commentRequest.getDescription(),
                commentRequest.getUserId());
    }
}