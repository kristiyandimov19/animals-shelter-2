package com.example.animalsshelter2.models.services;

public class CommentServiceModel {

    private Long authorId;

    private String description;

    private Long userId;

    public Long getAuthorId() {
        return authorId;
    }

    public CommentServiceModel setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CommentServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public CommentServiceModel setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}
