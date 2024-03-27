package com.libraryapi.bookservice.client;

import com.libraryapi.bookservice.dto.BookRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("library-service")
public interface LibraryClient {

    @PostMapping("/library/add")
    ResponseEntity<String> add(@RequestBody BookRequest bookRequest);

    @DeleteMapping("/library/delete")
    void delete(@RequestBody BookRequest bookRequest);
}
