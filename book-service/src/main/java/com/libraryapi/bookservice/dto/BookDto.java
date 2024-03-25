package com.libraryapi.bookservice.dto;

import lombok.Data;

@Data
public class BookDto {

    private int id;
    private String isbn;
    private String title;
    private String description;
    private String author;
    private String genre;
}

