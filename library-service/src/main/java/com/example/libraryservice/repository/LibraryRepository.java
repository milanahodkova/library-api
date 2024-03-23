package com.example.libraryservice.repository;

import com.example.libraryservice.model.LibraryBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryBookEntity, Integer> {
    List<LibraryBookEntity> findByReturnDueAtIsNull();

    Optional<LibraryBookEntity> findByBookId(int bookId);
}
