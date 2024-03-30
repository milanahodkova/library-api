package com.libraryapi.libraryservice.controller;

import com.libraryapi.libraryservice.dto.BookRequest;
import com.libraryapi.libraryservice.dto.LibraryBookRequest;
import com.libraryapi.libraryservice.dto.LibraryBookListResponse;
import com.libraryapi.libraryservice.dto.LibraryBookResponse;
import com.libraryapi.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/library")
@RequiredArgsConstructor
@Slf4j
public class LibraryBookController {

    private final LibraryService libraryService;

    @GetMapping
    public LibraryBookListResponse getAllBooks(){
        log.info("Fetching the list of books in the catalog");
        return libraryService.viewBookList();
    }

    @GetMapping("/available-books")
    public LibraryBookListResponse getAvailableBooks() {
        log.info("Fetching the list of available books in the catalog");
        return libraryService.viewAvailableBookList();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public LibraryBookResponse add(@RequestBody BookRequest bookRequest) {
        log.info("Adding book to the library with ID {}", bookRequest.getBookId());
        return libraryService.addBookToLibrary(bookRequest);
    }

    @PutMapping("/{bookId}")
    public LibraryBookResponse update(@PathVariable long bookId, @RequestBody LibraryBookRequest libraryBookRequest) {
        log.info("Updating book with ID {}", bookId);
        return libraryService.editLibraryBookDetails(bookId, libraryBookRequest);

    }

    @PostMapping("/borrow/{bookId}")
    public void borrowBook(@PathVariable long bookId) {
        log.info("Borrowing book with ID {}", bookId);
        libraryService.borrowBook(bookId);
    }

    @PostMapping("/return/{bookId}")
    public void returnBook(@PathVariable long bookId) {
        log.info("Returning book with ID {}", bookId);
        libraryService.returnBook(bookId);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody BookRequest bookRequest) {
        log.info("Deleting book with ID {}", bookRequest.getBookId());
        libraryService.deleteBook(bookRequest.getBookId());
    }
}
