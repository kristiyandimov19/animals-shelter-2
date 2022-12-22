package com.example.animalsshelter2.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "walk_history")
public class WalkHistory extends BaseEntity {
/*
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    private LocalDate localDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
    */

    private Long userId;

    private String animalName;

    private String animalType;

    private LocalDate localDate;

    public Long getUserId() {
        return userId;
    }

    public WalkHistory setUserId(Long userId) {
        this.userId = userId;
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
