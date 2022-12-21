package com.example.animalsshelter2.exceptions;

public class InvalidUserInputException extends Throwable{
    public InvalidUserInputException(final String message){
        super(message);
    }
}
