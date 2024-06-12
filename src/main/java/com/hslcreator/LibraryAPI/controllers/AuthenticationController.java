package com.hslcreator.LibraryAPI.controllers;


import com.hslcreator.LibraryAPI.models.requests.LoginRequest;
import com.hslcreator.LibraryAPI.models.requests.SignupRequest;
import com.hslcreator.LibraryAPI.models.responses.LoginResponse;
import com.hslcreator.LibraryAPI.models.responses.UserResponse;
import com.hslcreator.LibraryAPI.services.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("authentication/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("sign-up")
    @Transactional
    public LoginResponse signup(@RequestBody SignupRequest request) {

        return authenticationService.signup(request);
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return authenticationService.login(request);
    }
}
