package com.example.animalsshelter2.models.request;

import jakarta.validation.constraints.Email;

public class UserAuthRequest {
    @Email
    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public UserAuthRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserAuthRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
