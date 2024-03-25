package com.libraryapi.bookservice.exception;

public class BookAlreadyExistsException extends RuntimeException{
    public BookAlreadyExistsException(String isbn){
        super("The book with ISBN " + isbn + "already exists");
    }
}
