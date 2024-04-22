package com.libraryapi.libraryservice.controller.advice;

import com.libraryapi.libraryservice.dto.ErrorResponse;
import com.libraryapi.libraryservice.exception.BookAlreadyExistsException;
import com.libraryapi.libraryservice.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class LibraryBookControllerAdvice {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse bookNotFoundHandler(BookNotFoundException ex) {
        return new ErrorResponse("Book not found", HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse bookAlreadyExistsHandler(BookAlreadyExistsException ex) {
        return new ErrorResponse("Book already exists", HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    public ErrorResponse handleDataAccessException(DataAccessException ex) {
        return new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        return new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
