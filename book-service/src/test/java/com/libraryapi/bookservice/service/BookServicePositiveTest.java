package com.libraryapi.bookservice.service;

import com.libraryapi.bookservice.dto.BookClientRequest;
import com.libraryapi.bookservice.model.BookEntity;
import com.libraryapi.bookservice.service.config.BaseServiceUnitTest;
import com.libraryapi.bookservice.dto.BookListResponse;
import com.libraryapi.bookservice.dto.BookRequest;
import com.libraryapi.bookservice.dto.BookResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookServicePositiveTest extends BaseServiceUnitTest {

    @Test
    public void testViewBookList() {
        List<BookEntity> books = new ArrayList<>();
        books.add(new BookEntity());
        when(bookRepository.findAll()).thenReturn(books);

        BookListResponse response = bookService.viewBookList();

        assertNotNull(response);
        assertEquals(1, response.getBookResponseList().size());
    }

    @Test
    public void testViewBookDetailsById() {
        long id = 1L;
        BookEntity book = new BookEntity();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookResponse.class)).thenReturn(new BookResponse());

        BookResponse response = bookService.viewBookDetailsById(id);

        assertNotNull(response);
    }

    @Test
    public void testViewBookDetailsByIsbn() {
        String isbn = "978-3-16-148410-0";
        BookEntity book = new BookEntity();
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookResponse.class)).thenReturn(new BookResponse());

        BookResponse response = bookService.viewBookDetailsByIsbn(isbn);

        assertNotNull(response);
    }

    @Test
    public void testAddBookToCatalog() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setIsbn("9783161484101");
        bookRequest.setTitle("Book Title");
        bookRequest.setGenre("Science fiction");
        bookRequest.setDescription("Book description about test and something else.");
        bookRequest.setAuthor("Author A.I.");

        BookEntity bookEntity = new BookEntity();
        bookEntity.setIsbn("9783161484101");
        bookEntity.setTitle("Book Title");
        bookEntity.setGenre("Science fiction");
        bookEntity.setDescription("Book description about test and something else.");
        bookEntity.setAuthor("Author A.I.");

        when(modelMapper.map(bookRequest, BookEntity.class)).thenReturn(bookEntity);
        when(bookRepository.existsByIsbn(bookEntity.getIsbn())).thenReturn(false);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);

        BookResponse expectedResponse = new BookResponse();
        when(modelMapper.map(ArgumentMatchers.any(BookEntity.class), ArgumentMatchers.eq(BookResponse.class)))
                .thenReturn(expectedResponse);

        BookResponse response = bookService.addBookToCatalog(bookRequest);

        assertEquals(expectedResponse, response);
        verify(libraryClient, times(1)).add(any(BookClientRequest.class));
    }

    @Test
    public void testEditBookDetails() {
        long bookId = 1L;
        BookRequest updateBookDto = new BookRequest();
        updateBookDto.setIsbn("978-3-16-148410-0");
        updateBookDto.setTitle("New Book Title");
        updateBookDto.setGenre("New Genre");
        updateBookDto.setDescription("Book description about test and something else.");
        updateBookDto.setAuthor("New Author");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(book);

        bookService.editBookDetails(bookId, updateBookDto);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(any(BookEntity.class));

        assertEquals(bookId, book.getId());
        assertEquals(updateBookDto.getIsbn(), book.getIsbn());
        assertEquals(updateBookDto.getTitle(), book.getTitle());
        assertEquals(updateBookDto.getGenre(), book.getGenre());
        assertEquals(updateBookDto.getDescription(), book.getDescription());
        assertEquals(updateBookDto.getAuthor(), book.getAuthor());
    }

    @Test
    public void testRemoveBookFromCatalog() {
        long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        doNothing().when(libraryClient).delete(any(BookClientRequest.class));

        bookService.removeBookFromCatalog(id);

        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(1)).delete(book);
        verify(libraryClient, times(1)).delete(new BookClientRequest(id));
    }
}
