package com.libraryapi.bookservice;

import com.libraryapi.bookservice.dto.BookDto;
import com.libraryapi.bookservice.model.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<BookDto> json;

    @Test
    void testSerialize() throws Exception {
        var book = new BookDto(1, "9781234567890", "The Catcher in the Rye",
                "A novel about teenage angst", "J.D. Salinger", "Fiction");
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id")
                .isEqualTo((int) book.getId());
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.getIsbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.getTitle());
        assertThat(jsonContent).extractingJsonPathStringValue("@.description")
                .isEqualTo(book.getDescription());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.getAuthor());
        assertThat(jsonContent).extractingJsonPathStringValue("@.genre")
                .isEqualTo(book.getGenre());
    }

    @Test
    void testDeserialize() throws Exception {
        var content = """
                {
                    "id": 1,
                    "isbn": "9781234567890",
                    "title": "The Catcher in the Rye",
                    "description": "A novel about teenage angst",
                    "author": "J.D. Salinger",
                    "genre": "Fiction"
                  }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new BookDto(1L, "9781234567890", "The Catcher in the Rye", "A novel about teenage angst", "J.D. Salinger", "Fiction"));
    }
}
