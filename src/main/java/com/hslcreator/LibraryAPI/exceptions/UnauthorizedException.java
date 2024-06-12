package com.hslcreator.LibraryAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends ClientSideException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
