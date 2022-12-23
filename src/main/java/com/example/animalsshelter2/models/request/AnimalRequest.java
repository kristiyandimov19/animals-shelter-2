package com.example.animalsshelter2.models.request;

public class AnimalRequest {

    private Long id;
    private String name;
    private String type;
    private boolean availability = true;

    @Override
    public String toString() {
        return "AnimalServiceModel{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", availability=" + availability +
                '}';
    }

    public Long getId() {
        return id;
    }

    public AnimalRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AnimalRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public AnimalRequest setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isAvailability() {
        return availability;
    }

    public AnimalRequest setAvailability(boolean availability) {
        this.availability = availability;
        return this;
    }
}
