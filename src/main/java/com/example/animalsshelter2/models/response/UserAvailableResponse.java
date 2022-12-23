package com.example.animalsshelter2.models.response;

public class UserAvailableResponse {

    private Long id;

    private String username;

    public Long getId() {
        return id;
    }

    public UserAvailableResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserAvailableResponse setUsername(String username) {
        this.username = username;
        return this;
    }

}
