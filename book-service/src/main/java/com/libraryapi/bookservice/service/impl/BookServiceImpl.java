package com.libraryapi.bookservice.service.impl;

import com.libraryapi.bookservice.client.LibraryClient;
import com.libraryapi.bookservice.dto.BookClientRequest;
import com.libraryapi.bookservice.dto.BookListResponse;
import com.libraryapi.bookservice.dto.BookRequest;
import com.libraryapi.bookservice.dto.BookResponse;
import com.libraryapi.bookservice.exception.BookAlreadyExistsException;
import com.libraryapi.bookservice.exception.BookNotFoundException;
import com.libraryapi.bookservice.model.BookEntity;
import com.libraryapi.bookservice.repository.BookRepository;
import com.libraryapi.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final LibraryClient libraryClient;

    public BookListResponse viewBookList() {
        List<BookEntity> books = bookRepository.findAll();
        return new BookListResponse(
                books.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList())
        );
    }

    public BookResponse viewBookDetailsById(long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return convertToDto(book);
    }

    public BookResponse viewBookDetailsByIsbn(String isbn) {
        BookEntity book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        return convertToDto(book);
    }

    public BookResponse addBookToCatalog(BookRequest bookRequest) {
        BookEntity book = convertToEntity(bookRequest);
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlreadyExistsException(book.getIsbn());
        }
        BookEntity savedBook = bookRepository.save(book);
        libraryClient.add(new BookClientRequest(savedBook.getId()));
        return convertToDto(savedBook);
    }

    public BookResponse editBookDetails(long id, BookRequest bookRequest) {
        BookEntity updatedBook = bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setIsbn(bookRequest.getIsbn());
                    existingBook.setTitle(bookRequest.getTitle());
                    existingBook.setDescription(bookRequest.getDescription());
                    existingBook.setAuthor(bookRequest.getAuthor());
                    existingBook.setGenre(bookRequest.getGenre());
                    return bookRepository.save(existingBook);
                })
                .orElseThrow(() -> new BookNotFoundException(id));

        return convertToDto(updatedBook);
    }

    public BookResponse removeBookFromCatalog(long id) {
        BookEntity deletedBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(deletedBook);
        libraryClient.delete(new BookClientRequest(deletedBook.getId()));
        return convertToDto(deletedBook);
    }

    private BookResponse convertToDto(BookEntity book) {
        return modelMapper.map(book, BookResponse.class);
    }

    private BookEntity convertToEntity(BookRequest bookRequest) {
        return modelMapper.map(bookRequest, BookEntity.class);
    }
}
