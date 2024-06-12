package com.hslcreator.LibraryAPI.exceptions;

public class BookRequestNotFoundException extends EntityNotFoundException{
    public BookRequestNotFoundException() {
        super("BOOK REQUEST");
    }
}
