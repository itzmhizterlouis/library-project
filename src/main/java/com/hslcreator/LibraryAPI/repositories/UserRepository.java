package com.hslcreator.LibraryAPI.repositories;

import com.hslcreator.LibraryAPI.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByMatricNumber(String matricNumber);

    Optional<User> findByUserId(int userId);
}
