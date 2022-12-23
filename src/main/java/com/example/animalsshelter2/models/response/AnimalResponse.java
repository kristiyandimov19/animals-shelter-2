package com.example.animalsshelter2.models.response;

public class AnimalResponse {

    private Long id;
    private String name;
    private String type;
    private boolean availability;

    public Long getId() {
        return id;
    }

    public AnimalResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AnimalResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public AnimalResponse setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isAvailability() {
        return availability;
    }

    public AnimalResponse setAvailability(boolean availability) {
        this.availability = availability;
        return this;
    }
}
