package com.libraryapi.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private long id;
    private String isbn;
    private String title;
    private String description;
    private String author;
    private String genre;
}

