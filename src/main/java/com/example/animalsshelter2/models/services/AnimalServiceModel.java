package com.example.animalsshelter2.models.services;

import com.example.animalsshelter2.models.views.AnimalViewModel;

public class AnimalServiceModel {

    private Long id;
    private String name;
    private String type;
    private boolean availability;

    private String User;

    @Override
    public String toString() {
        return "AnimalServiceModel{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", availability=" + availability +
                ", User='" + User + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public AnimalServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUser() {
        return User;
    }

    public AnimalServiceModel setUser(String user) {
        User = user;
        return this;
    }

    public String getName() {
        return name;
    }

    public AnimalServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public AnimalServiceModel setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isAvailability() {
        return availability;
    }

    public AnimalServiceModel setAvailability(boolean availability) {
        this.availability = availability;
        return this;
    }
}
