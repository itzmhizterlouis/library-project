package com.hslcreator.LibraryAPI.repositories;

import com.hslcreator.LibraryAPI.models.entities.Book;
import com.hslcreator.LibraryAPI.models.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Book> {

    Optional<Message> findAllByUserIdAndAdminId(int userId, int adminId);
}
