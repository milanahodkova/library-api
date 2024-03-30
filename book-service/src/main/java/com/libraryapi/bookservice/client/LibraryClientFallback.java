package com.libraryapi.bookservice.client;

import com.libraryapi.bookservice.dto.BookClientRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibraryClientFallback implements LibraryClient{

    @Override
    public ResponseEntity<String> add(BookClientRequest bookClientRequest) {
        log.error("Unable to access the library service. The book could not be added to the library. Book = {}",
                bookClientRequest);
        return ResponseEntity.internalServerError().body("Unable to access the library service.");
    }

    @Override
    public void delete(BookClientRequest bookClientRequest) {
        log.error("Unable to access the library service. Book deletion failed. Book = {}", bookClientRequest);
    }
}
