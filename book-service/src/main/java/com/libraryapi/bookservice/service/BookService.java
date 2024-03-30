package com.libraryapi.bookservice.service;

import com.libraryapi.bookservice.dto.BookListResponse;
import com.libraryapi.bookservice.dto.BookRequest;
import com.libraryapi.bookservice.dto.BookResponse;

import java.util.List;

public interface BookService {
    BookListResponse viewBookList();

    BookResponse viewBookDetailsById(long id);

    BookResponse viewBookDetailsByIsbn(String isbn);

    BookResponse addBookToCatalog(BookRequest bookRequest);

    BookResponse editBookDetails(long id, BookRequest bookRequest);

    BookResponse removeBookFromCatalog(long id);
}
