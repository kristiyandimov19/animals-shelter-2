package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.Comment;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.repositories.CommentRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.services.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment createComment(Long authorId, String description, Long userId) {

        User author = userRepository.findById(authorId).orElse(null);

        Comment comment = new Comment()
                .setDescription(description)
                .setAuthor(author.getUsername());

        commentRepository.save(comment);

        return comment;
    }
}
