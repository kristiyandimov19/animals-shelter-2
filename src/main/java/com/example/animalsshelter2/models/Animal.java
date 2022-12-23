package com.example.animalsshelter2.models;

import jakarta.persistence.*;

@Entity
@Table(name = "animals")
public class Animal extends BaseEntity {

    public Animal() {
    }

    public Animal(String name, String type, boolean availability, User user) {
        this.name = name;
        this.type = type;
        this.availability = availability;
        this.user = user;
    }


    private String name;


    private String type;


    private boolean availability;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public Animal setUser(User user) {
        this.user = user;
        return this;
    }

    public String getName() {
        return name;
    }

    public Animal setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Animal setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isAvailability() {
        return availability;
    }

    public Animal setAvailability(boolean availability) {
        this.availability = availability;
        return this;
    }
}
