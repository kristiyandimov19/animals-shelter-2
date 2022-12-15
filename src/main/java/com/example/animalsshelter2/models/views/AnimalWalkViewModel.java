package com.example.animalsshelter2.models.views;

public class AnimalWalkViewModel {

    private Long id;

    private String name;

    private String type;

    private String username;

    public Long getId() {
        return id;
    }

    public AnimalWalkViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AnimalWalkViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public AnimalWalkViewModel setType(String type) {
        this.type = type;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AnimalWalkViewModel setUsername(String username) {
        this.username = username;
        return this;
    }
}
