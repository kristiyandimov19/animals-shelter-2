package com.example.animalsshelter2.exceptions;

public class  ForbiddenOperationException extends Throwable{
    public ForbiddenOperationException(final String message){
        super(message);
    }
}
