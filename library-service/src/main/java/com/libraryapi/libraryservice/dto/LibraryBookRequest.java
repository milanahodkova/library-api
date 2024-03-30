package com.libraryapi.libraryservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LibraryBookRequest {
    private long bookId;
    private LocalDateTime takenAt;
    private LocalDateTime returnDueAt;

}
