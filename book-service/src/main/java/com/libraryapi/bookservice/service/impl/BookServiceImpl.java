package com.libraryapi.bookservice.service.impl;

import com.libraryapi.bookservice.client.LibraryClient;
import com.libraryapi.bookservice.dto.BookClientRequest;
import com.libraryapi.bookservice.dto.BookListResponse;
import com.libraryapi.bookservice.dto.BookRequest;
import com.libraryapi.bookservice.dto.BookResponse;
import com.libraryapi.bookservice.exception.BookAlreadyExistsException;
import com.libraryapi.bookservice.exception.BookNotFoundException;
import com.libraryapi.bookservice.exception.ServiceUnavailableException;
import com.libraryapi.bookservice.model.BookEntity;
import com.libraryapi.bookservice.repository.BookRepository;
import com.libraryapi.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                        .toList()
        );
    }

    public BookResponse viewBookDetailsById(long id) {
        BookEntity book = findByIdOrThrow(id);;
        return convertToDto(book);
    }

    public BookResponse viewBookDetailsByIsbn(String isbn) {
        BookEntity book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        return convertToDto(book);
    }

    @Transactional(rollbackFor=ServiceUnavailableException.class)
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
        BookEntity updatedBook = findByIdOrThrow(id);
        updatedBook.setIsbn(bookRequest.getIsbn());
        updatedBook.setTitle(bookRequest.getTitle());
        updatedBook.setDescription(bookRequest.getDescription());
        updatedBook.setAuthor(bookRequest.getAuthor());
        updatedBook.setGenre(bookRequest.getGenre());
        return convertToDto(bookRepository.save(updatedBook));
    }

    @Transactional(rollbackFor=ServiceUnavailableException.class)
    public BookResponse removeBookFromCatalog(long id) {
        BookEntity deletedBook = findByIdOrThrow(id);
        bookRepository.delete(deletedBook);
        libraryClient.delete(new BookClientRequest(deletedBook.getId()));
        return convertToDto(deletedBook);
    }

    private BookEntity findByIdOrThrow(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
    private BookResponse convertToDto(BookEntity book) {
        return modelMapper.map(book, BookResponse.class);
    }

    private BookEntity convertToEntity(BookRequest bookRequest) {
        return modelMapper.map(bookRequest, BookEntity.class);
    }
}
