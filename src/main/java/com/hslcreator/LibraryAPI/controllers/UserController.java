package com.hslcreator.LibraryAPI.controllers;


import com.hslcreator.LibraryAPI.exceptions.UserNotFoundException;
import com.hslcreator.LibraryAPI.models.responses.UserResponse;
import com.hslcreator.LibraryAPI.repositories.UserRepository;
import com.hslcreator.LibraryAPI.services.UserService;
import com.hslcreator.LibraryAPI.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("users/")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("me")
    public UserResponse getLoggedInUser() {

        return UserUtil.getLoggedInUser().orElseThrow(UserNotFoundException::new).toDto();
    }
}
