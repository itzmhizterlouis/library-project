package com.hslcreator.LibraryAPI.services;


import com.hslcreator.LibraryAPI.exceptions.UnauthorizedException;
import com.hslcreator.LibraryAPI.exceptions.UserNotFoundException;
import com.hslcreator.LibraryAPI.models.entities.User;
import com.hslcreator.LibraryAPI.models.responses.GenericResponse;
import com.hslcreator.LibraryAPI.models.responses.UserResponse;
import com.hslcreator.LibraryAPI.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<UserResponse> findAllUsers() throws UnauthorizedException {

        LibraryService.throwErrorIfUserNotAdmin();
        return userRepository.findAll().stream().map(User::toDto).toList();
    }

    public GenericResponse suspendAccount(int userId, boolean suspend) throws UnauthorizedException {

        LibraryService.throwErrorIfUserNotAdmin();

        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
        user.setLocked(suspend);
        userRepository.save(user);

        return GenericResponse.builder()
                .message("User with id " + userId + " suspend status has been set to " + suspend)
                .build();
    }
}
