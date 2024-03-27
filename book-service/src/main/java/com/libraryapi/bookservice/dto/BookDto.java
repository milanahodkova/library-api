package com.libraryapi.bookservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private long id;
    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
    private String isbn;

    @NotBlank(message = "The book title must be defined.")
    private String title;

    @NotBlank(message = "The book description must be defined.")
    private String description;

    @NotBlank(message = "The book author must be defined.")
    private String author;

    @NotBlank(message = "The book genre must be defined.")
    private String genre;
}

