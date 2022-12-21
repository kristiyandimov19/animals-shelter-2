package com.example.animalsshelter2.exceptions;

public class DuplicateEntityException extends Throwable{
    public DuplicateEntityException(final String message){
        super(message);
    }
}
