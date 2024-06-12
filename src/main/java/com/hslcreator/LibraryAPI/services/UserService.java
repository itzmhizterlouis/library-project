package com.hslcreator.LibraryAPI.services;


import com.hslcreator.LibraryAPI.exceptions.UserNotFoundException;
import com.hslcreator.LibraryAPI.models.entities.User;
import com.hslcreator.LibraryAPI.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User findUserByMatricNumber(String matricNumber) throws UserNotFoundException {

        return userRepository.findByMatricNumber(matricNumber).orElseThrow(UserNotFoundException::new);
    }

    public User findUserById(int id) throws UserNotFoundException {

        return userRepository.findByUserId(id).orElseThrow(UserNotFoundException::new);
    }
}
