package com.example.animalsshelter2.models.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public class LoginResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }
}