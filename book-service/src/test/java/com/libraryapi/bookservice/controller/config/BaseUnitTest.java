package com.libraryapi.bookservice.controller.config;

import com.libraryapi.bookservice.controller.BookController;
import com.libraryapi.bookservice.dto.BookResponse;
import com.libraryapi.bookservice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
public class BaseUnitTest {
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected BookService bookService;

    protected BookResponse bookResponse;

    @BeforeEach
    public void setup() {
        bookResponse = new BookResponse();
        bookResponse.setId(1L);
        bookResponse.setIsbn("9783161484100");
        bookResponse.setTitle("Book Title");
        bookResponse.setGenre("Science fiction");
        bookResponse.setDescription("Book description about test and something else.");
        bookResponse.setAuthor("Author A.I.");
    }
}
