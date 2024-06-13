package com.hslcreator.LibraryAPI.controllers;


import com.hslcreator.LibraryAPI.models.requests.LoginRequest;
import com.hslcreator.LibraryAPI.models.requests.SignupRequest;
import com.hslcreator.LibraryAPI.models.responses.LoginResponse;
import com.hslcreator.LibraryAPI.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountLockedException;

@RequiredArgsConstructor
@RestController
@RequestMapping("authentication/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "User Signup", description = "Endpoint for signing up user also returns a validated jwt token")
    @PostMapping("sign-up")
    @Transactional
    public LoginResponse signup(@RequestBody SignupRequest request) {

        return authenticationService.signup(request);
    }

    @Operation(summary = "Login User", description = "Returns a validated jwt token that can be used to log in")
    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest request) throws AccountLockedException {

        return authenticationService.login(request);
    }
}
