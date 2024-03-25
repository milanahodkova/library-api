package com.libraryapi.libraryservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LibraryBookDto {
    private long bookId;
    private LocalDateTime takenAt;
    private LocalDateTime returnDueAt;

}
