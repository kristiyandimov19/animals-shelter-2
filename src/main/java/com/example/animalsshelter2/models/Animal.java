package com.example.animalsshelter2.models;

import jakarta.persistence.*;

@Entity
@Table(name = "animals")
public class Animal extends BaseEntity {

    public Animal() {
    }

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "availability")
    private boolean availability;

    @OneToOne
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
