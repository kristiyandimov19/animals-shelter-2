package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.Comment;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.UserRole;
import com.example.animalsshelter2.models.enums.UserRoleEnum;
import com.example.animalsshelter2.models.response.CommentResponse;
import com.example.animalsshelter2.repositories.CommentRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.services.impl.CommentServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;

    private CommentServiceImpl commentService;

    private User user, author;

    private final UserRole userRole = new UserRole(UserRoleEnum.USER);

    private Comment comment;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(userRole);

        author = new User();
        author.setId(2L);
        author.setEmail("author@gmail.com");
        author.setUsername("author");
        author.setPassword("author");
        author.setRole(new UserRole(UserRoleEnum.ADMIN));

        comment = new Comment();
        comment.setAuthor(author.getUsername());
        comment.setId(1L);
        comment.setUser(user);
        comment.setDescription("na marto kambanite");

        commentService = new CommentServiceImpl(commentRepository, userRepository, new ModelMapper());
    }

    @Test
    void createComment() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.ofNullable(user));

        when(userRepository.findById(2L))
                .thenReturn(Optional.ofNullable(author));

        Comment comment = commentService.createComment(2L, "description", 1L);

        assertEquals("description", comment.getDescription());
        assertEquals(user, comment.getUser());
        assertEquals(author.getUsername(), comment.getAuthor());

        verify(userRepository, atLeast(4)).findById(anyLong());
    }

    @Test
    void findAllByUserId() {

        when(commentRepository.findAllByUserId(anyLong()))
                .thenReturn(List.of(comment));

        List<CommentResponse> actualResult = commentService.findAllByUserId(user.getId());

        assertEquals(1, actualResult.size());
        assertEquals(comment.getAuthor(), actualResult.get(0).getAuthor());
        assertEquals(comment.getDescription(), actualResult.get(0).getDescription());
        verify(commentRepository).findAllByUserId(1L);
    }

    @Test
    void findAllComments_ShouldThrowExceptionResponseStatusException() {
        when(commentRepository.findAllByUserId(anyLong()))
                .thenThrow(new EntityNotFoundException());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> commentService.findAllByUserId(1L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User has no comments", exception.getReason());
    }

    @Test
    void createComment_ShouldThrowResponseStatusException() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> commentService.createComment(2L, "description", 1L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User was not found.", exception.getReason());
        verify(userRepository).findById(anyLong());
    }
}