package com.hslcreator.LibraryAPI.repositories;

import com.hslcreator.LibraryAPI.models.entities.BookRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {

    Optional<BookRequest> findByBookRequestId(int id);
}
