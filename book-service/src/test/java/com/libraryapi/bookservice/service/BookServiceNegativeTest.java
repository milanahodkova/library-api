package com.libraryapi.bookservice.service;

import com.libraryapi.bookservice.dto.BookRequest;
import com.libraryapi.bookservice.exception.BookAlreadyExistsException;
import com.libraryapi.bookservice.exception.BookNotFoundException;
import com.libraryapi.bookservice.model.BookEntity;
import com.libraryapi.bookservice.service.config.BaseServiceUnitTest;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class BookServiceNegativeTest extends BaseServiceUnitTest {
    @Test
    public void testViewBookDetailsById_BookNotFound() {
        long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.viewBookDetailsById(id));
    }

    @Test
    public void testViewBookDetailsByIsbn_BookNotFound() {
        String isbn = "978-3-16-148410-0";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.viewBookDetailsByIsbn(isbn));
    }

    @Test
    public void testAddBookToCatalog_BookAlreadyExists() {
        BookRequest bookRequest = new BookRequest();
        BookEntity bookEntity = new BookEntity();
        when(modelMapper.map(bookRequest, BookEntity.class)).thenReturn(bookEntity);
        when(bookRepository.existsByIsbn(bookEntity.getIsbn())).thenReturn(true);

        assertThrows(BookAlreadyExistsException.class, () -> bookService.addBookToCatalog(bookRequest));
    }

    @Test
    public void testEditBookDetails_BookNotFound() {
        long id = 1L;
        BookRequest bookRequest = new BookRequest();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.editBookDetails(id, bookRequest));
    }

    @Test
    public void testRemoveBookFromCatalog_BookNotFound() {
        long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.removeBookFromCatalog(id));
    }
}
