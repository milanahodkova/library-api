package com.libraryapi.bookservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryapi.bookservice.controller.config.BaseUnitTest;
import com.libraryapi.bookservice.dto.BookListResponse;
import com.libraryapi.bookservice.dto.BookRequest;
import com.libraryapi.bookservice.dto.BookResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerPositiveTest extends BaseUnitTest {
    @Test
    public void testGetBookList() throws Exception {
        // Arrange
        List<BookResponse> bookList = Arrays.asList(
                new BookResponse(1, "1234567890", "Book 1", "Description 1", "Author 1", "Genre 1"),
                new BookResponse(2, "0987654321", "Book 2", "Description 2", "Author 2", "Genre 2")
        );
        BookListResponse expectedResponse = new BookListResponse(bookList);

        when(bookService.viewBookList()).thenReturn(expectedResponse);

        // Act and Assert
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookResponseList", hasSize(2)))
                .andExpect(jsonPath("$.bookResponseList[0].id", is(1)))
                .andExpect(jsonPath("$.bookResponseList[0].isbn", is("1234567890")))
                .andExpect(jsonPath("$.bookResponseList[0].title", is("Book 1")))
                .andExpect(jsonPath("$.bookResponseList[0].description", is("Description 1")))
                .andExpect(jsonPath("$.bookResponseList[0].author", is("Author 1")))
                .andExpect(jsonPath("$.bookResponseList[0].genre", is("Genre 1")))
                .andExpect(jsonPath("$.bookResponseList[1].id", is(2)))
                .andExpect(jsonPath("$.bookResponseList[1].isbn", is("0987654321")))
                .andExpect(jsonPath("$.bookResponseList[1].title", is("Book 2")))
                .andExpect(jsonPath("$.bookResponseList[1].description", is("Description 2")))
                .andExpect(jsonPath("$.bookResponseList[1].author", is("Author 2")))
                .andExpect(jsonPath("$.bookResponseList[1].genre", is("Genre 2")));

        verify(bookService, times(1)).viewBookList();
    }


    @Test
    public void testGetBookById() throws Exception {
        long bookId = 1;
        BookResponse bookResponse = new BookResponse(bookId, "1234567890", "Book 1", "Description 1", "Author 1", "Genre 1");
        when(bookService.viewBookDetailsById(bookId)).thenReturn(bookResponse);

        mockMvc.perform(get("/api/v1/books/id/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.isbn", is("1234567890")))
                .andExpect(jsonPath("$.title", is("Book 1")))
                .andExpect(jsonPath("$.description", is("Description 1")))
                .andExpect(jsonPath("$.author", is("Author 1")))
                .andExpect(jsonPath("$.genre", is("Genre 1")));

        verify(bookService, times(1)).viewBookDetailsById(bookId);
    }

    @Test
    public void testGetBookByIsbn() throws Exception {
        // Arrange
        String isbn = "1234567890";
        BookResponse expectedResponse = new BookResponse(1, isbn, "Book 1", "Description 1", "Author 1", "Genre 1");

        when(bookService.viewBookDetailsByIsbn(isbn)).thenReturn(expectedResponse);

        // Act and Assert
        mockMvc.perform(get("/api/v1/books/isbn/{isbn}", isbn))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isbn", is(isbn)))
                .andExpect(jsonPath("$.title", is("Book 1")))
                .andExpect(jsonPath("$.description", is("Description 1")))
                .andExpect(jsonPath("$.author", is("Author 1")))
                .andExpect(jsonPath("$.genre", is("Genre 1")));

        verify(bookService, times(1)).viewBookDetailsByIsbn(isbn);
    }

    @Test
    public void testAddBook() throws Exception {
        // Arrange
        BookRequest bookRequest = new BookRequest("1234567890", "Book 1", "Description 1", "Author 1", "Genre 1");
        BookResponse expectedResponse = new BookResponse(1, "1234567890", "Book 1","Description 1", "Author 1", "Genre 1");

        when(bookService.addBookToCatalog(bookRequest)).thenReturn(expectedResponse);

        // Act and Assert
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.isbn", is("1234567890")))
                .andExpect(jsonPath("$.title", is("Book 1")))
                .andExpect(jsonPath("$.description", is("Description 1")))
                .andExpect(jsonPath("$.author", is("Author 1")))
                .andExpect(jsonPath("$.genre", is("Genre 1")));

        verify(bookService, times(1)).addBookToCatalog(bookRequest);
    }

    @Test
    public void testUpdateBook() throws Exception {
        long bookId = 1;
        BookRequest bookRequest = new BookRequest("1234567890", "Book 1", "Description 1", "Author 1", "Genre 1");
        BookResponse expectedResponse = new BookResponse(bookId, "1234567890", "Book 1", "Description 1", "Author 1", "Genre 1");

        when(bookService.editBookDetails(bookId, bookRequest)).thenReturn(expectedResponse);

        mockMvc.perform(put("/api/v1/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is((int) bookId)))
                .andExpect(jsonPath("$.isbn", is("1234567890")))
                .andExpect(jsonPath("$.title", is("Book 1")))
                .andExpect(jsonPath("$.description", is("Description 1")))
                .andExpect(jsonPath("$.author", is("Author 1")))
                .andExpect(jsonPath("$.genre", is("Genre 1")));

        verify(bookService, times(1)).editBookDetails(bookId, bookRequest);
    }

    @Test
    public void testDeleteBook() throws Exception {
        long bookId = 1;
        BookResponse expectedResponse = new BookResponse(bookId, "1234567890", "Book 1", "Description 1", "Author 1", "Genre 1");

        when(bookService.removeBookFromCatalog(bookId)).thenReturn(expectedResponse);

        mockMvc.perform(delete("/api/v1/books/{id}", bookId))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).removeBookFromCatalog(bookId);
    }



}
