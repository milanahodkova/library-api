package com.libraryapi.bookservice.service;

import com.libraryapi.bookservice.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> viewBookList();

    BookDto viewBookDetailsById(long id);

    BookDto viewBookDetailsByIsbn(String isbn);

    BookDto addBookToCatalog(BookDto bookDto);

    BookDto editBookDetails(long id, BookDto bookDto);

    BookDto removeBookFromCatalog(long id);
}
