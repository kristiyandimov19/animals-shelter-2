package com.example.animalsshelter2.models.services;

import jakarta.validation.constraints.Email;

public class UserAuthServiceModel {
    @Email
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
