package com.example.animalsshelter2.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "walk_history")
public class WalkHistory extends BaseEntity {

    @ManyToOne
    private User user;

    private String animalName;

    private String animalType;

    private LocalDate localDate;

    public User getUser() {
        return user;
    }

    public WalkHistory setUser(User user) {
        this.user = user;
        return this;
    }

    public String getAnimalName() {
        return animalName;
    }

    public WalkHistory setAnimalName(String animalName) {
        this.animalName = animalName;
        return this;
    }

    public String getAnimalType() {
        return animalType;
    }

    public WalkHistory setAnimalType(String animalType) {
        this.animalType = animalType;
        return this;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
