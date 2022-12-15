package com.example.animalsshelter2.models.views;

public class UserAvailableViewModel {

    private Long id;

    private String username;

    public Long getId() {
        return id;
    }

    public UserAvailableViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserAvailableViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

}
