package com.hslcreator.LibraryAPI.exceptions;

public class InvalidTokenException extends UnauthorizedException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
