package com.libraryapi.libraryservice.service.impl;

import com.libraryapi.libraryservice.dto.BookRequest;
import com.libraryapi.libraryservice.dto.LibraryBookRequest;
import com.libraryapi.libraryservice.dto.LibraryBookListResponse;
import com.libraryapi.libraryservice.dto.LibraryBookResponse;
import com.libraryapi.libraryservice.exception.BookAlreadyExistsException;
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
    private static final long BORROW_DURATION_DAYS = 30;
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;

    public LibraryBookListResponse viewBookList() {
        List<LibraryBookEntity> allBooks = libraryRepository.findAll();
        return new LibraryBookListResponse(
                allBooks.stream()
                        .map(this::convertToDto)
                        .toList()
        );
    }

    public LibraryBookListResponse viewAvailableBookList() {
        List<LibraryBookEntity> availableBooks = libraryRepository.findByReturnDueAtIsNull();
        return new LibraryBookListResponse(
                availableBooks.stream()
                        .map(this::convertToDto)
                        .toList()
        );
    }

    public LibraryBookResponse editLibraryBookDetails(long bookId, LibraryBookRequest libraryBookRequest) {
        LibraryBookEntity libraryBookEntity = findByIdOrThrow(bookId);
        libraryBookEntity.setBorrowedAt(libraryBookRequest.getBorrowedAt());
        libraryBookEntity.setReturnDueAt(libraryBookRequest.getReturnDueAt());
        libraryRepository.save(libraryBookEntity);
        log.info("Book details were edited successfully. Book ID: {}", bookId);
        return convertToDto(libraryBookEntity);
    }

    public LibraryBookResponse addBookToLibrary(BookRequest bookRequest) {
        log.info("Adding book to the library.Book ID: {}", bookRequest.getBookId());
        LibraryBookEntity libraryBook = new LibraryBookEntity();
        if (libraryRepository.existsByBookId(bookRequest.getBookId())) {
            throw new BookAlreadyExistsException(bookRequest.getBookId());
        }
        libraryBook.setBookId(bookRequest.getBookId());
        libraryBook.setBorrowedAt(null);
        libraryBook.setReturnDueAt(null);
        libraryRepository.save(libraryBook);

        log.info("Book was added to the library successfully.Book ID: {}", bookRequest.getBookId());
        return convertToDto(libraryBook);
    }

    public void borrowBook(long bookId) {
        LibraryBookEntity book = findByIdOrThrow(bookId);
        LocalDateTime borrowedAt = LocalDateTime.now();
        LocalDateTime returnDueAt = borrowedAt.plusDays(BORROW_DURATION_DAYS);
        book.setBorrowedAt(borrowedAt);
        book.setReturnDueAt(returnDueAt);

        libraryRepository.save(book);
        log.info("Book was borrowed successfully. Book ID: {}", bookId);
    }

    public void returnBook(long bookId) {
        LibraryBookEntity book = findByIdOrThrow(bookId);
        book.setBorrowedAt(null);
        book.setReturnDueAt(null);

        libraryRepository.save(book);
        log.info("Book was returned successfully. Book ID: {}", bookId);
    }

    public void deleteBook(long bookId) {
        LibraryBookEntity book = findByIdOrThrow(bookId);

        libraryRepository.delete(book);
        log.info("Book was deleted successfully. Book ID: {}", bookId);;
    }

    private LibraryBookResponse convertToDto(LibraryBookEntity book) {
        return modelMapper.map(book, LibraryBookResponse.class);
    }

    private LibraryBookEntity findByIdOrThrow(long bookId) {
        return libraryRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }
}
