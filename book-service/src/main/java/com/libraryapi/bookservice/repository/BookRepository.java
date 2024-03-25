package com.libraryapi.bookservice.repository;

import com.libraryapi.bookservice.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Integer> {

    Optional<BookEntity> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);

    Optional<BookEntity> findById(long id);
}
