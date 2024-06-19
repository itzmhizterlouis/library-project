package com.hslcreator.LibraryAPI.controllers;


import com.hslcreator.LibraryAPI.exceptions.UnauthorizedException;
import com.hslcreator.LibraryAPI.exceptions.UserNotFoundException;
import com.hslcreator.LibraryAPI.models.requests.SuspendAccountRequest;
import com.hslcreator.LibraryAPI.models.responses.GenericResponse;
import com.hslcreator.LibraryAPI.models.responses.UserResponse;
import com.hslcreator.LibraryAPI.services.UserService;
import com.hslcreator.LibraryAPI.utils.UserUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("users/")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get Logged in user", description = "Get user details for logged in user")
    @GetMapping("me")
    public UserResponse getLoggedInUser() {

        return UserUtil.getLoggedInUser().orElseThrow(UserNotFoundException::new).toDto();
    }

    @Operation(summary = "Get All Users")
    @GetMapping("all")
    public List<UserResponse> findAllUsers() throws UnauthorizedException {

        return userService.findAllUsers();
    }

    @Operation(summary = "Suspend/Unsuspend Account", description = "Set the boolean suspend account to true or false depending on whether or whether not you want to suspend an account")
    @PostMapping("account/suspend/{userId}")
    public GenericResponse suspendAccount(@PathVariable int userId, @RequestBody SuspendAccountRequest request) throws UnauthorizedException {

        return userService.suspendAccount(userId, request.isSuspend());
    }
}
