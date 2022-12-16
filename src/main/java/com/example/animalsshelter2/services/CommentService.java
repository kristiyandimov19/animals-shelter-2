package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.Comment;

public interface CommentService {

    Comment createComment(Long authorId, String description, Long userId);
}
