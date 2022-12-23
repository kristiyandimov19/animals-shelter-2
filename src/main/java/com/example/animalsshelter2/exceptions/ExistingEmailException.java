package com.example.animalsshelter2.exceptions;

public class ExistingEmailException extends Throwable {

    public ExistingEmailException(final String message) {
        super(message);
    }

}
