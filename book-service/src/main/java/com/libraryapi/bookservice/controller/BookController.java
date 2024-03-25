package com.libraryapi.bookservice.controller;

import com.libraryapi.bookservice.dto.BookDto;
import com.libraryapi.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;

    @GetMapping()
    public List<BookDto> get() {
        log.info("Fetching the list of books in the catalog");
        return bookService.viewBookList();
    }

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable long id) {
        log.info("Fetching the book with ID {} from the catalog", id);
        return bookService.viewBookDetailsById(id);
    }

    @GetMapping("/{isbn}")
    public BookDto getByIsbn(@PathVariable String isbn) {
        log.info("Fetching the book with ISBN {} from the catalog", isbn);
        return bookService.viewBookDetailsByIsbn(isbn);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto add(@RequestBody BookDto bookDto) {
        log.info("Adding a new book to the catalog with ISBN {}", bookDto.getIsbn());
        return bookService.addBookToCatalog(bookDto);
    }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable long id, @RequestBody BookDto bookDto) {
        log.info("Updating book with ID {}", id);
        return bookService.editBookDetails(id, bookDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        log.info("Deleting book with ID {}", id);
        bookService.removeBookFromCatalog(id);
    }
}
