package com.libraryapi.libraryservice.exception;

public class BookNotFoundException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "The book with ID %d was not found.";

    public BookNotFoundException(long id) {
        super(String.format(MESSAGE_FORMAT, id));
    }
}
