package com.example.animalsshelter2.models.request;

public class RegisterRequest {

    private String email;

    private String password;


    private String username;

    public RegisterRequest(String email, String password, String username) {
        this.email = email;
        this.password = password;

        this.username = username;
    }

    public RegisterRequest() {
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
