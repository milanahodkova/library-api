package com.libraryapi.libraryservice.service;

import com.libraryapi.libraryservice.dto.BookRequest;
import com.libraryapi.libraryservice.dto.LibraryBookRequest;
import com.libraryapi.libraryservice.dto.LibraryBookListResponse;
import com.libraryapi.libraryservice.dto.LibraryBookResponse;

import java.util.List;

public interface LibraryService {
    LibraryBookListResponse viewAvailableBookList();

    LibraryBookResponse editLibraryBookDetails(long bookId, LibraryBookRequest libraryBookRequest);

    LibraryBookResponse addBookToLibrary(BookRequest bookRequest);

    void borrowBook(long bookId);

    void returnBook(long bookId);

    void deleteBook(long bookId);

    LibraryBookListResponse viewBookList();
}
