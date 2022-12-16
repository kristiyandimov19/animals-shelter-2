package com.example.animalsshelter2.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    public User() {
    }

    public User(String email, String password, boolean isAdmin, Animal animal) {
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.animal = animal;
    }

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "admin")
    private boolean isAdmin = false;

    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    public List<Comment> getComments() {
        return comments;
    }

    public User setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public User setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
