package com.example.libraryservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LibraryBookDto {
    private int bookId;
    private LocalDateTime takenAt;
    private LocalDateTime returnDueAt;

}
