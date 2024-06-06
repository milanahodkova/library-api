package com.libraryapi.libraryservice.exception;

public class BookAlreadyExistsException extends RuntimeException{
    private static final String MESSAGE_FORMAT = "The book with ID %d already exists.";

    public BookAlreadyExistsException(long id) {
        super(String.format(MESSAGE_FORMAT,  id));
    }
}
