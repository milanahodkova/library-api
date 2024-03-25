package com.libraryapi.bookservice.controller;

import com.libraryapi.bookservice.exception.BookNotFoundException;
import com.libraryapi.bookservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BookControllerMvcTests {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "73737313940";
        given(bookService.viewBookDetailsByIsbn(isbn))
                .willThrow(BookNotFoundException.class);
        mockMvc
                .perform(get("/books/isbn/" + isbn))
                .andExpect(status().isNotFound());
    }
}
