package com.libraryapi.bookservice.controller.advice;

import com.libraryapi.bookservice.dto.ErrorResponse;
import com.libraryapi.bookservice.exception.BookAlreadyExistsException;
import com.libraryapi.bookservice.exception.BookNotFoundException;
import com.libraryapi.bookservice.exception.ServiceUnavailableException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class BookControllerAdvice {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse bookNotFoundHandler(BookNotFoundException ex) {
        return new ErrorResponse("Not Found", HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse bookAlreadyExistsHandler(BookAlreadyExistsException ex) {
        return new ErrorResponse("Unprocessable Entity", HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse serviceUnavailableHandler(ServiceUnavailableException ex) {
        return new ErrorResponse("Service Unavailable", HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ErrorResponse("Bad Request", HttpStatus.BAD_REQUEST.value(), errors.toString());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    public ErrorResponse handleDataAccessException(DataAccessException ex) {
        return new ErrorResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        return new ErrorResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
