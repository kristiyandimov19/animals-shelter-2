package com.example.animalsshelter2.models.response;

public class CommentResponse {

    private String author;

    private String description;

    public String getAuthor() {
        return author;
    }

    public CommentResponse setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CommentResponse setDescription(String description) {
        this.description = description;
        return this;
    }
}
