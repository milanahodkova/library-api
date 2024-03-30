package com.libraryapi.libraryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryBookListResponse {
    private List<LibraryBookResponse> bookTrackerRequestList;
}
