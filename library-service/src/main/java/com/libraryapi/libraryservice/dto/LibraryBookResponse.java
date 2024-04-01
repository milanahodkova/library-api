package com.libraryapi.libraryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryBookResponse {
    private long id;
    private long bookId;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnDueAt;
}
