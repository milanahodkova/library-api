package com.example.libraryservice.service.impl;

import com.example.libraryservice.dto.BookRequest;
import com.example.libraryservice.dto.LibraryBookDto;
import com.example.libraryservice.exception.NotFoundException;
import com.example.libraryservice.model.LibraryBookEntity;
import com.example.libraryservice.repository.LibraryRepository;
import com.example.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;

    public List<LibraryBookDto> getFreeBooks() {
        List<LibraryBookEntity> freeBooks = libraryRepository.findByReturnDueAtIsNull();
        return freeBooks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public LibraryBookDto updateBook(int bookId, LibraryBookDto libraryBookDto) {
        LibraryBookEntity libraryBookEntity = libraryRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with id"+bookId));
        libraryBookEntity.setBorrowedAt(libraryBookDto.getTakenAt());
        libraryBookEntity.setReturnDueAt(libraryBookDto.getReturnDueAt());
        libraryRepository.save(libraryBookEntity);
        return convertToDto(libraryBookEntity);
    }

    public LibraryBookDto addBook(BookRequest bookRequest) {
        LibraryBookEntity libraryBook = new LibraryBookEntity();
        libraryBook.setBookId(bookRequest.getBookId());
        libraryBook.setBorrowedAt(null);
        libraryBook.setReturnDueAt(null);
        libraryRepository.save(libraryBook);

        return convertToDto(libraryBook);

    }

    public void borrowBook(int bookId) {
        Optional<LibraryBookEntity> optionalLibraryEntity = Optional.ofNullable(getById(bookId));
        if(optionalLibraryEntity.isPresent()){
            LocalDateTime borrowedAt = LocalDateTime.now();
            LocalDateTime returnDueAt = borrowedAt.plusMonths(1);

            LibraryBookEntity libraryBookEntity = optionalLibraryEntity.get();
            libraryBookEntity.setBorrowedAt(borrowedAt);
            libraryBookEntity.setReturnDueAt(returnDueAt);
            libraryRepository.save(libraryBookEntity);
        }else{
            throw new NotFoundException("Book with ID " + bookId + " not found.");
        }
    }

    public void returnBook(int bookId) {
        Optional<LibraryBookEntity> optionalLibraryEntity = libraryRepository.findByBookId(bookId);
        if (optionalLibraryEntity.isPresent()) {
            LibraryBookEntity libraryBookEntity = optionalLibraryEntity.get();
            libraryBookEntity.setBorrowedAt(null);
            libraryBookEntity.setReturnDueAt(null);
            libraryRepository.save(libraryBookEntity);
        } else {
            throw new NotFoundException("Book with ID " + bookId + " not found.");
        }
    }

    public void deleteBook(int bookId) {
        LibraryBookEntity libraryBookEntity = getById(bookId);

        libraryRepository.delete(libraryBookEntity);
    }

    private LibraryBookDto convertToDto(LibraryBookEntity book) {
        return modelMapper.map(book, LibraryBookDto.class);
    }

    private LibraryBookEntity convertToEntity(LibraryBookDto bookDto) {
        return modelMapper.map(bookDto, LibraryBookEntity.class);
    }

    private LibraryBookEntity getById(int bookId) {
        return libraryRepository.findByBookId(bookId)
                .orElseThrow(() -> new NotFoundException("Library entry not found for bookId: " + bookId));
    }
}
