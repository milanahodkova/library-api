package com.example.libraryservice.controller;

import com.example.libraryservice.dto.BookRequest;
import com.example.libraryservice.dto.LibraryBookDto;
import com.example.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping("/add")
    public ResponseEntity<LibraryBookDto> addBook(@RequestBody BookRequest bookRequest){
        LibraryBookDto addedBook = libraryService.addBook(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body((addedBook));
    }

    @GetMapping("/getFreeBooks")
    public ResponseEntity<List<LibraryBookDto>> getFreeBooks(){
        List<LibraryBookDto> freeBooks = libraryService.getFreeBooks();
        return new ResponseEntity<>(freeBooks, HttpStatus.OK);
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<LibraryBookDto> updateBook(@PathVariable int bookId, @RequestBody LibraryBookDto libraryBookDto){
        LibraryBookDto updatedBook = libraryService.updateBook(bookId, libraryBookDto);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<String>  borrowBook(@PathVariable int bookId){
        libraryService.borrowBook(bookId);
        return ResponseEntity.ok("Book borrowed successfully");
    }

    @PostMapping("/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable int bookId){
        libraryService.returnBook(bookId);
        return ResponseEntity.ok("Book returned successfully");
    }

    @DeleteMapping("/delete/{bookId}")
    public void deleteBook(@PathVariable int bookId) {
        libraryService.deleteBook(bookId);
    }
}
