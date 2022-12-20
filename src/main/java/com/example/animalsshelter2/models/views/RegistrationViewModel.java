package com.example.animalsshelter2.models.views;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationViewModel {
    @NotBlank(message = "Password can`t be empty")
    private String password;

    @NotBlank
    @Email(message = "This is not a valid mail.Please try again.")
    private String email;

    private String username;

    public RegistrationViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public RegistrationViewModel setUsername(String username) {
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
