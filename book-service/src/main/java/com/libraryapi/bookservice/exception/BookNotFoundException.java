package com.libraryapi.bookservice.exception;

public class BookNotFoundException extends RuntimeException {
    private static final String MESSAGE_FORMAT_ISBN = "The book with ISBN %s was not found.";
    private static final String MESSAGE_FORMAT_ID = "The book with ID %d was not found.";

    public BookNotFoundException(String isbn) {
        super(String.format(MESSAGE_FORMAT_ISBN, isbn));
    }

    public BookNotFoundException(long id) {
        super(String.format(MESSAGE_FORMAT_ID, id));
    }
}