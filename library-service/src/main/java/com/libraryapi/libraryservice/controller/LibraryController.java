package com.libraryapi.libraryservice.controller;

import com.libraryapi.libraryservice.dto.BookRequest;
import com.libraryapi.libraryservice.dto.LibraryBookDto;
import com.libraryapi.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
@Slf4j
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/availableBooks")
    public List<LibraryBookDto> getAvailableBooks() {
        log.info("Fetching the list of available books in the catalog");
        return libraryService.viewAvailableBookList();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public LibraryBookDto add(@RequestBody BookRequest bookRequest) {
        log.info("Adding book to the library with ID {}", bookRequest.getBookId());
        return libraryService.addBookToLibrary(bookRequest);
    }

    @PutMapping("/update/{bookId}")
    public LibraryBookDto updateBook(@PathVariable long bookId, @RequestBody LibraryBookDto libraryBookDto) {
        log.info("Updating book with ID {}", bookId);
        return libraryService.editLibraryBookDetails(bookId, libraryBookDto);

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

    @DeleteMapping("/delete/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable long bookId) {
        log.info("Deleting book with ID {}", bookId);
        libraryService.deleteBook(bookId);
    }
}
