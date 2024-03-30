package com.libraryapi.bookservice.controller;

import com.libraryapi.bookservice.dto.BookListResponse;
import com.libraryapi.bookservice.dto.BookRequest;
import com.libraryapi.bookservice.dto.BookResponse;
import com.libraryapi.bookservice.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;

    @GetMapping()
    public BookListResponse get() {
        log.info("Fetching the list of books in the catalog");
        return bookService.viewBookList();
    }

    @GetMapping("/id/{id}")
    public BookResponse getById(@PathVariable long id) {
        log.info("Fetching the book with ID {} from the catalog", id);
        return bookService.viewBookDetailsById(id);
    }

    @GetMapping("/isbn/{isbn}")
    public BookResponse getByIsbn(@PathVariable String isbn) {
        log.info("Fetching the book with ISBN {} from the catalog", isbn);
        return bookService.viewBookDetailsByIsbn(isbn);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse add(@Valid @RequestBody BookRequest bookRequest) {
        log.info("Adding a new book to the catalog with ISBN {}", bookRequest.getIsbn());
        return bookService.addBookToCatalog(bookRequest);
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable long id, @Valid @RequestBody BookRequest bookRequest) {
        log.info("Updating book with ID {}", id);
        return bookService.editBookDetails(id, bookRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BookResponse delete(@PathVariable long id) {
        log.info("Deleting book with ID {}", id);
        return bookService.removeBookFromCatalog(id);
    }
}
