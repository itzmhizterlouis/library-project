package com.hslcreator.LibraryAPI.exceptions;

public class UserNotFoundException extends EntityNotFoundException{

    public UserNotFoundException() {

        super("USER");
    }
}
