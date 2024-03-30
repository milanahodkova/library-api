package com.libraryapi.bookservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {

    private static final String ISBN_REGEX = "^([0-9]{10}|[0-9]{13})$";

    @NotBlank(message = "message.validation.isbn")
    @Pattern(regexp = ISBN_REGEX,
            message = "message.validation.isbn.format")
    private String isbn;

    @NotBlank(message = "message.validation.title")
    private String title;

    @NotBlank(message = "message.validation.description")
    private String description;

    @NotBlank(message = "message.validation.author")
    private String author;

    @NotBlank(message = "message.validation.genre")
    private String genre;
}