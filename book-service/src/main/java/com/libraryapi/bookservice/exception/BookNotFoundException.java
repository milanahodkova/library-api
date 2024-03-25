package com.libraryapi.bookservice.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn){
        super("The book with ISBN " + isbn + " was not found.");
    }

    public BookNotFoundException(long id){
        super("The book with ID " + id + " was not found.");
    }
}
