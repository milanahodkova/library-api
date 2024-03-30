package com.libraryapi.bookservice.client;

import com.libraryapi.bookservice.dto.BookClientRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "library-service", fallback = LibraryClientFallback.class)
public interface LibraryClient {

    @PostMapping("/api/v1/library/add")
    ResponseEntity<String> add(@RequestBody BookClientRequest bookClientRequest);

    @DeleteMapping("/api/v1/library/delete")
    void delete(@RequestBody BookClientRequest bookClientRequest);
}
