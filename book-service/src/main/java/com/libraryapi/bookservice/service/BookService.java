package com.libraryapi.bookservice.service;

import com.libraryapi.bookservice.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> findAll();

    BookDto findBookById(int id);

    BookDto findBookByIsbn(String isbn);

    BookDto addBook(BookDto bookDto);

    BookDto updateBook(int id, BookDto bookDto);

    void deleteBook(int id);
}
