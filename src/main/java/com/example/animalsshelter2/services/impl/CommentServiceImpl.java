package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.Comment;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.response.CommentResponse;
import com.example.animalsshelter2.repositories.CommentRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.services.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Comment createComment(Long authorId, String description, Long userId) {

        if (userRepository.findById(authorId).isPresent() &&
                userRepository.findById(userId).isPresent()) {
            User author = userRepository.findById(authorId).get();
            User user = userRepository.findById(userId).get();
            Comment comment = new Comment()
                    .setDescription(description)
                    .setAuthor(author.getUsername())
                    .setUser(user);
            commentRepository.save(comment);
            return comment;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found.");
        }

    }

    @Override
    public List<CommentResponse> findAllByUserId(Long id) {

        try {
            List<Comment> comments = commentRepository.findAllByUserId(id);

            if (comments.size() > 5) {
                comments = comments
                        .subList(comments.size() - 5, comments.size());
            }
            Collections.reverse(comments);
            return comments.stream()
                    .map(c -> modelMapper.map(c, CommentResponse.class))
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User has no comments", e);
        }
    }
}
