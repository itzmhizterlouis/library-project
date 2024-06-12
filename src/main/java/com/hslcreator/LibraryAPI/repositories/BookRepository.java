package com.hslcreator.LibraryAPI.repositories;

import com.hslcreator.LibraryAPI.models.entities.Book;
import com.hslcreator.LibraryAPI.models.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByBookId(Integer bookId);
}
