package com.example.animalsshelter2.models;

import jakarta.persistence.*;

@Entity
@Table(name = "animals")
public class Animal extends BaseEntity {

    public Animal() {
    }

    public Animal(String name, String type, boolean availability) {
        this.name = name;
        this.type = type;
        this.availability = availability;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "availability")
    private boolean availability = true;

    public String getName() {
        return name;
    }

    public Animal setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Animal setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isAvailability() {
        return availability;
    }

    public Animal setAvailability(boolean availability) {
        this.availability = availability;
        return this;
    }
}
