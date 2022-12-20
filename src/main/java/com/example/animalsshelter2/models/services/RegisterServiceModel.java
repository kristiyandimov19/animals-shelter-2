package com.example.animalsshelter2.models.services;

public class RegisterServiceModel {

    private String email;

    private String password;


    private String username;
    public RegisterServiceModel(String email, String password, String username) {
        this.email = email;
        this.password = password;

        this.username = username;
    }

    public RegisterServiceModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return "user";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
