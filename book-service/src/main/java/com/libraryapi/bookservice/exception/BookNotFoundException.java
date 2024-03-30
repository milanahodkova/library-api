package com.libraryapi.bookservice.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) {
        super(String.format("The book with ISBN %s was not found.", isbn));
    }

    public BookNotFoundException(long id) {
        super(String.format("The book with ID %d was not found.", id));
    }
}