package com.libraryapi.bookservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryapi.bookservice.controller.config.BaseUnitTest;
import com.libraryapi.bookservice.dto.BookRequest;
import com.libraryapi.bookservice.exception.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerNegativeTest extends BaseUnitTest {
    @Test
    public void testGetBookByIdBookNotFound() throws Exception {
        String exceptionMessage = "The book with ID 2 was not found.";
        when(bookService.viewBookDetailsById(2L)).thenThrow(new BookNotFoundException(2));

        mockMvc.perform(get("/api/v1/books/id/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", equalTo("Not Found")))
                .andExpect(jsonPath("$.status", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo(exceptionMessage)));
    }

    @Test
    public void testGetBookByIsbnBookNotFound() throws Exception {
        String exceptionMessage = "The book with ISBN 73737313940 was not found.";
        when(bookService.viewBookDetailsByIsbn("73737313940")).thenThrow(new BookNotFoundException("73737313940"));

        mockMvc.perform(get("/api/v1/books/isbn/73737313940"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", equalTo("Not Found")))
                .andExpect(jsonPath("$.status", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo(exceptionMessage)));
    }

    @Test
    public void testUpdateBookNotFound() throws Exception {
        long bookId = 2L;
        BookRequest bookRequest = new BookRequest();
        bookRequest.setIsbn("7373731394000");
        bookRequest.setTitle("Update Book Title");
        bookRequest.setAuthor("Update Author");
        bookRequest.setDescription("Update Description");
        bookRequest.setGenre("Update Genre");

        String exceptionMessage = "The book with ID 2 was not found.";
        doThrow(new BookNotFoundException(bookId)).when(bookService).editBookDetails(bookId, bookRequest);

        mockMvc.perform(put("/api/v1/books/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", equalTo("Not Found")))
                .andExpect(jsonPath("$.status", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo(exceptionMessage)));
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {
        long bookId = 2L;
        String exceptionMessage = "The book with ID 2 was not found.";
        doThrow(new BookNotFoundException(bookId)).when(bookService).removeBookFromCatalog(bookId);
        mockMvc.perform(delete("/api/v1/books/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", equalTo("Not Found")))
                .andExpect(jsonPath("$.status", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo(exceptionMessage)));
    }

    @Test
    public void testAddBookInvalidData() throws Exception {
        BookRequest invalidBookRequest = new BookRequest();
        invalidBookRequest.setIsbn("111");
        invalidBookRequest.setTitle("");
        invalidBookRequest.setAuthor("");
        invalidBookRequest.setDescription("");
        invalidBookRequest.setGenre("");

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidBookRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateBookInvalidData() throws Exception {
        BookRequest invalidBookRequest = new BookRequest();
        invalidBookRequest.setIsbn("111");
        invalidBookRequest.setTitle("");
        invalidBookRequest.setAuthor("");
        invalidBookRequest.setDescription("");
        invalidBookRequest.setGenre("");

        mockMvc.perform(put("/api/v1/books/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidBookRequest)))
                .andExpect(status().isBadRequest());
    }



}
