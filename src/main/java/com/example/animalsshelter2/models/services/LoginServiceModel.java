package com.example.animalsshelter2.models.services;

public class LoginServiceModel {
    private String email;

    private String password;


    public LoginServiceModel() {
    }

    public LoginServiceModel(String email, String password) {
        this.email = email;
        this.password = password;

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


}
