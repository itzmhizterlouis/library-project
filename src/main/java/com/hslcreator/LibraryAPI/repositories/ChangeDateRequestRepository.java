package com.hslcreator.LibraryAPI.repositories;

import com.hslcreator.LibraryAPI.models.entities.ChangeDateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ChangeDateRequestRepository extends JpaRepository<ChangeDateRequest, Long> {

    boolean existsByBookRequestId(int bookRequestId);
    Optional<ChangeDateRequest> findByBookRequestId(int bookRequestId);
}
