package com.libraryapi.bookservice.client;

import com.libraryapi.bookservice.dto.BookClientRequest;
import com.libraryapi.bookservice.exception.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class  LibraryClientFallback implements LibraryClient {

    private static final String ADD_ERROR_MESSAGE = "Unable to access the library service. " +
            "The book with ID %s could not be added to the library.";
    private static final String DELETE_ERROR_MESSAGE = "Unable to access the library service. " +
            "Failed to delete the book with ID %s.";

    @Override
    public ResponseEntity<String> add(BookClientRequest bookClientRequest) {
        log.error(String.format(ADD_ERROR_MESSAGE, bookClientRequest.getBookId()));
        throw new ServiceUnavailableException(String.format(ADD_ERROR_MESSAGE, bookClientRequest.getBookId()));
    }

    @Override
    public ResponseEntity<String> delete(BookClientRequest bookClientRequest) {
        log.error(String.format(DELETE_ERROR_MESSAGE, bookClientRequest.getBookId()));
        throw new ServiceUnavailableException(String.format(DELETE_ERROR_MESSAGE, bookClientRequest.getBookId()));
    }
}

