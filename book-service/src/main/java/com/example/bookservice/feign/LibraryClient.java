package com.example.bookservice.feign;

import com.example.bookservice.dto.BookRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("LIBRARY-SERVICE")
public interface LibraryClient {

    @PostMapping("/library/add")
    ResponseEntity<String> addBook(@RequestBody BookRequest bookRequest);

    @DeleteMapping("/library/delete")
    public void deleteBook(@RequestParam int bookId);
}
