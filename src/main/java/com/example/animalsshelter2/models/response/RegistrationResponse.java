package com.example.animalsshelter2.models.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegistrationResponse {
    @NotBlank(message = "Password can`t be empty")
    private String password;

    @NotBlank
    @Email(message = "This is not a valid mail.Please try again.")
    private String email;

    private String username;

    public RegistrationResponse() {
    }

    public String getUsername() {
        return username;
    }

    public RegistrationResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
