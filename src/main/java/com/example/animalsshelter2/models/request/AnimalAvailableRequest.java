package com.example.animalsshelter2.models.request;

public class AnimalAvailableRequest {

    private Long id;

    private boolean availability;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public AnimalAvailableRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public boolean isAvailability() {
        return availability;
    }

    public AnimalAvailableRequest setAvailability(boolean availability) {
        this.availability = availability;
        return this;
    }

    public Long getId() {
        return id;
    }

    public AnimalAvailableRequest setId(Long id) {
        this.id = id;
        return this;
    }
}
