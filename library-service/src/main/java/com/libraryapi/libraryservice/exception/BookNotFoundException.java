package com.libraryapi.libraryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long id){
        super("The book with ID " + id + " was not found.");
    }
}
