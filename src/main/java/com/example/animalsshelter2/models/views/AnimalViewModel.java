package com.example.animalsshelter2.models.views;

public class AnimalViewModel {

    private Long id;
    private String name;
    private String type;
    private boolean availability;

    public Long getId() {
        return id;
    }

    public AnimalViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AnimalViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public AnimalViewModel setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isAvailability() {
        return availability;
    }

    public AnimalViewModel setAvailability(boolean availability) {
        this.availability = availability;
        return this;
    }
}
