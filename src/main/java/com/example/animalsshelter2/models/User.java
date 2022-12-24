package com.example.animalsshelter2.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    public User() {
    }

    public User(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String username, String email, String password, UserRole role, Animal animal) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.animal = animal;
    }

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @ManyToOne
    private UserRole role;

    @OneToOne
    private Animal animal;

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

    public UserRole getRole() {
        return role;
    }

    public User setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public Animal getAnimal() {
        return animal;
    }

    public User setAnimal(Animal animal) {
        this.animal = animal;
        return this;
    }

}