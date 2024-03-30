package com.libraryapi.libraryservice.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long id) {
        super("The book with ID " + id + " was not found.");
    }
}
