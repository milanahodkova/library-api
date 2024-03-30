package com.libraryapi.libraryservice.service.impl;

import com.libraryapi.libraryservice.dto.BookRequest;
import com.libraryapi.libraryservice.dto.LibraryBookDto;
import com.libraryapi.libraryservice.exception.BookNotFoundException;
import com.libraryapi.libraryservice.model.LibraryBookEntity;
import com.libraryapi.libraryservice.repository.LibraryRepository;
import com.libraryapi.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;

    public List<LibraryBookDto> viewAvailableBookList() {
        List<LibraryBookEntity> availableBooks = libraryRepository.findByReturnDueAtIsNull();
        return availableBooks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public LibraryBookDto editLibraryBookDetails(long bookId, LibraryBookDto libraryBookDto) {
        LibraryBookEntity libraryBookEntity = libraryRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        libraryBookEntity.setBorrowedAt(libraryBookDto.getTakenAt());
        libraryBookEntity.setReturnDueAt(libraryBookDto.getReturnDueAt());
        libraryRepository.save(libraryBookEntity);
        return convertToDto(libraryBookEntity);
    }

    public LibraryBookDto addBookToLibrary(BookRequest bookRequest) {
        LibraryBookEntity libraryBook = new LibraryBookEntity();
        libraryBook.setBookId(bookRequest.getBookId());
        libraryBook.setBorrowedAt(null);
        libraryBook.setReturnDueAt(null);
        libraryRepository.save(libraryBook);

        log.info("Book was added to the library successfully.");
        return convertToDto(libraryBook);
    }

    public void borrowBook(long bookId) {
        LibraryBookEntity book = getById(bookId);
        LocalDateTime borrowedAt = LocalDateTime.now();
        LocalDateTime returnDueAt = borrowedAt.plusMonths(1);
        book.setBorrowedAt(borrowedAt);
        book.setReturnDueAt(returnDueAt);

        libraryRepository.save(book);
        log.info("Book was borrowed successfully.");
    }

    public void returnBook(long bookId) {
        LibraryBookEntity book = getById(bookId);
        book.setBorrowedAt(null);
        book.setReturnDueAt(null);

        libraryRepository.save(book);
        log.info("Book was returned successfully.");
    }

    public void deleteBook(long bookId) {
        LibraryBookEntity book = getById(bookId);

        libraryRepository.delete(book);
        log.info("Book was deleted successfully.");
    }

    private LibraryBookDto convertToDto(LibraryBookEntity book) {
        return modelMapper.map(book, LibraryBookDto.class);
    }

    private LibraryBookEntity convertToEntity(LibraryBookDto bookDto) {
        return modelMapper.map(bookDto, LibraryBookEntity.class);
    }

    private LibraryBookEntity getById(long bookId) {
        return libraryRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }
}
