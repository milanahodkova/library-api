package com.libraryapi.libraryservice.service;

import com.libraryapi.libraryservice.dto.BookRequest;
import com.libraryapi.libraryservice.dto.LibraryBookDto;

import java.util.List;

public interface LibraryService {
    List<LibraryBookDto> viewAvailableBookList();

    LibraryBookDto editLibraryBookDetails(long bookId, LibraryBookDto libraryBookDto);

    LibraryBookDto addBookToLibrary(BookRequest bookRequest);

    void borrowBook(long bookId);

    void returnBook(long bookId);

    void deleteBook(long bookId);
}
