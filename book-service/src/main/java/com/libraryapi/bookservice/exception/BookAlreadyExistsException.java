package com.libraryapi.bookservice.exception;

public class BookAlreadyExistsException extends RuntimeException{
    public BookAlreadyExistsException(String isbn){
        super(String.format("The book with ISBN %s already exists", isbn));
    }
}
