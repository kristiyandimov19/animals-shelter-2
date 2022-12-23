package com.example.animalsshelter2.models.response;

public class AnimalWalkResponse {

    private Long id;

    private String name;

    private String type;

    private String username;

    public Long getId() {
        return id;
    }

    public AnimalWalkResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AnimalWalkResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public AnimalWalkResponse setType(String type) {
        this.type = type;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AnimalWalkResponse setUsername(String username) {
        this.username = username;
        return this;
    }
}
