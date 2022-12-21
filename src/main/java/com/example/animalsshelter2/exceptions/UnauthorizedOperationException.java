package com.example.animalsshelter2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;


public class UnauthorizedOperationException extends Throwable  {

    public UnauthorizedOperationException(final String message){
        super(message);
    }
}
