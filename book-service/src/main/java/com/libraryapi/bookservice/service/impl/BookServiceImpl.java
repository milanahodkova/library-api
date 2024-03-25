package com.libraryapi.bookservice.service.impl;

import com.libraryapi.bookservice.dto.BookDto;
import com.libraryapi.bookservice.dto.BookRequest;
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
    public List<BookDto> findAll() {
        List<BookEntity> books = bookRepository.findAll();
        return books.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookDto findBookById(int id) {
        BookEntity book = getById(id);
        return convertToDto(book);
    }

    public BookDto findBookByIsbn(String isbn) {
        BookEntity book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new BookNotFoundException("Book not found with ISBN: " + isbn);
        }
        return convertToDto(book);
    }

    public BookDto addBook(BookDto bookDto) {
        BookEntity book = convertToEntity(bookDto);
        BookEntity savedBook = bookRepository.save(book);

        libraryClient.addBook(new BookRequest(savedBook.getId()));

        return convertToDto(savedBook);
    }

    public BookDto updateBook(int id, BookDto bookDto) {
        BookEntity existingBook = getById(id);
        existingBook.setIsbn(bookDto.getIsbn());
        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setGenre(bookDto.getGenre());
        existingBook.setDescription(bookDto.getDescription());

        BookEntity updatedBook = bookRepository.save(existingBook);

        return convertToDto(updatedBook);
    }

    public void deleteBook(int id) {
        BookEntity book = getById(id);
        bookRepository.delete(book);
        libraryClient.deleteBook(id);
    }

    private BookDto convertToDto(BookEntity book) {
        return modelMapper.map(book, BookDto.class);
    }

    private BookEntity convertToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }

    private BookEntity getById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }
}
