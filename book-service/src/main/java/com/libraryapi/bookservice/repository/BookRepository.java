package com.libraryapi.bookservice.repository;

import com.libraryapi.bookservice.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Integer> {

    BookEntity findByIsbn(String isbn);
}
