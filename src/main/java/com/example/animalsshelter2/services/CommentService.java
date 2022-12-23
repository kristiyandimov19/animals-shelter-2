package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.Comment;
import com.example.animalsshelter2.models.response.CommentResponse;

import java.util.List;

public interface CommentService {

    Comment createComment(Long authorId, String description, Long userId);

    List<CommentResponse> findAllByUserId(Long id);
}
