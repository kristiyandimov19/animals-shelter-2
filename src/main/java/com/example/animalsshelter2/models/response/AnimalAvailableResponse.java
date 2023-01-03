package com.example.animalsshelter2.models.response;

public class AnimalAvailableResponse {

    private Long id;

    private boolean availability;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public AnimalAvailableResponse setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public boolean isAvailability() {
        return availability;
    }

    public AnimalAvailableResponse setAvailability(boolean availability) {
        this.availability = availability;
        return this;
    }

    public Long getId() {
        return id;
    }

    public AnimalAvailableResponse setId(Long id) {
        this.id = id;
        return this;
    }
}
