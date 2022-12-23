package com.example.animalsshelter2.models.request;

public class CommentRequest {

    private Long authorId;

    private String description;

    private Long userId;

    public Long getAuthorId() {
        return authorId;
    }

    public CommentRequest setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CommentRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public CommentRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}
