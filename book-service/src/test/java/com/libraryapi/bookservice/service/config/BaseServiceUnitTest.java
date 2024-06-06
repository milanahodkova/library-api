package com.libraryapi.bookservice.service.config;

import com.libraryapi.bookservice.client.LibraryClient;
import com.libraryapi.bookservice.dto.BookClientRequest;
import com.libraryapi.bookservice.dto.BookResponse;
import com.libraryapi.bookservice.model.BookEntity;
import com.libraryapi.bookservice.repository.BookRepository;
import com.libraryapi.bookservice.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class BaseServiceUnitTest {
    @InjectMocks
    protected BookServiceImpl bookService;
    @Mock
    protected BookRepository bookRepository;
    @Mock
    protected LibraryClient libraryClient;
    @Mock
    protected ModelMapper modelMapper;

    protected BookEntity book;
    protected BookResponse bookResponse;

    @BeforeEach
    public void setupBook() {
        book = new BookEntity();
        book.setId(1L);
        book.setIsbn("1233445578945");
        book.setTitle("Book Title");
        book.setGenre("Genre");
        book.setDescription("Book description.");
        book.setAuthor("Author");
    }

    @BeforeEach
    public void setupBookDto() {
        bookResponse = new BookResponse();
        bookResponse.setId(1L);
        bookResponse.setIsbn("1233445578945");
        bookResponse.setTitle("Book Title");
        bookResponse.setGenre("Genre");
        bookResponse.setDescription("Book description.");
        bookResponse.setAuthor("Author");
    }
}
