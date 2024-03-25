package com.libraryapi.bookservice.service.impl;

import com.libraryapi.bookservice.dto.BookDto;
import com.libraryapi.bookservice.dto.BookRequest;
import com.libraryapi.bookservice.exception.BookAlreadyExistsException;
import com.libraryapi.bookservice.exception.BookNotFoundException;
import com.libraryapi.bookservice.feign.LibraryClient;
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

    public List<BookDto> viewBookList() {
        List<BookEntity> books = bookRepository.findAll();
        return books.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookDto viewBookDetailsById(long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return convertToDto(book);
    }

    public BookDto viewBookDetailsByIsbn(String isbn) {
        BookEntity book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        return convertToDto(book);
    }

    public BookDto addBookToCatalog(BookDto bookDto) {
        BookEntity book = convertToEntity(bookDto);
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlreadyExistsException(book.getIsbn());
        }
        BookEntity savedBook = bookRepository.save(book);

        libraryClient.addBook(new BookRequest(savedBook.getId()));

        return convertToDto(savedBook);
    }

    public BookDto editBookDetails(long id, BookDto bookDto) {
        BookEntity updatedBook = bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(bookDto.getTitle());
                    existingBook.setDescription(bookDto.getDescription());
                    existingBook.setAuthor(bookDto.getAuthor());
                    existingBook.setGenre(bookDto.getGenre());
                    return bookRepository.save(existingBook);
                })
                .orElseThrow(() -> new BookNotFoundException(id));

        return convertToDto(updatedBook);
    }

    public void removeBookFromCatalog(long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(book);
        libraryClient.deleteBook(id);
    }

    private BookDto convertToDto(BookEntity book) {
        return modelMapper.map(book, BookDto.class);
    }

    private BookEntity convertToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
