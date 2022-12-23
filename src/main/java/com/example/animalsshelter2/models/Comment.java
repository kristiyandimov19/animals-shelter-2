package com.example.animalsshelter2.models;


import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String description;


    public String getAuthor() {
        return author;
    }

    public Comment setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Comment setDescription(String description) {
        this.description = description;
        return this;
    }
}
