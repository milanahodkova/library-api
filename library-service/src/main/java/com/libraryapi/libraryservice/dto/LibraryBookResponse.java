package com.libraryapi.libraryservice.dto;

import java.time.LocalDateTime;

public class LibraryBookResponse {
    private long id;
    private long bookId;
    private LocalDateTime takenAt;
    private LocalDateTime returnDueAt;
}
