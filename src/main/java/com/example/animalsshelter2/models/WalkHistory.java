package com.example.animalsshelter2.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "walk_history")
public class WalkHistory extends BaseEntity {

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
