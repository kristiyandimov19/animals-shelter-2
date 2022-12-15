package com.example.animalsshelter2.models.services;

public class AnimalAvailableServiceModel {

    private Long id;

    private boolean availability;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public AnimalAvailableServiceModel setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public boolean isAvailability() {
        return availability;
    }

    public AnimalAvailableServiceModel setAvailability(boolean availability) {
        this.availability = availability;
        return this;
    }

    public Long getId() {
        return id;
    }

    public AnimalAvailableServiceModel setId(Long id) {
        this.id = id;
        return this;
    }
}
