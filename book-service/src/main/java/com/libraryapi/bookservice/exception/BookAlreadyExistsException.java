package com.libraryapi.bookservice.exception;

public class BookAlreadyExistsException extends RuntimeException{
    private static final String MESSAGE_FORMAT = "The book with %s %s already exists.";

    public BookAlreadyExistsException(String isbn) {
        super(String.format(MESSAGE_FORMAT, "ISBN", isbn));
    }
}
