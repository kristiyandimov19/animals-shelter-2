package com.example.animalsshelter2.models.services;

public class UserAuthServiceModel {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public UserAuthServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserAuthServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
