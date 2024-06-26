package com.libraryapi.bookservice.client;

import com.libraryapi.bookservice.client.fallback.LibraryClientFallback;
import com.libraryapi.bookservice.dto.BookClientRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${feign.clients.library.name}", fallback = LibraryClientFallback.class)
public interface LibraryClient {

    @PostMapping("/api/v1/library")
    void add(@RequestBody BookClientRequest bookClientRequest);

    @DeleteMapping("/api/v1/library")
    void delete(@RequestBody BookClientRequest bookClientRequest);
}
