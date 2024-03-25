package com.libraryapi.libraryservice.service;

import com.libraryapi.libraryservice.dto.BookRequest;
import com.libraryapi.libraryservice.dto.LibraryBookDto;

import java.util.List;

public interface LibraryService {
    List<LibraryBookDto> getFreeBooks();

    LibraryBookDto updateBook(int bookId, LibraryBookDto libraryBookDto);

    LibraryBookDto addBook(BookRequest bookRequest);

    void borrowBook(int bookId);

    void returnBook(int bookId);

    void deleteBook(int bookId);
}
