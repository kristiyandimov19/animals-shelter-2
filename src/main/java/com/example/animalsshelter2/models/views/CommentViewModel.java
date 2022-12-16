package com.example.animalsshelter2.models.views;

public class CommentViewModel {

    private String author;

    private String description;

    public String getAuthor() {
        return author;
    }

    public CommentViewModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CommentViewModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
