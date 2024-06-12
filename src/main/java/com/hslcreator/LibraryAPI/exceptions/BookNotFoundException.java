package com.hslcreator.LibraryAPI.exceptions;

public class BookNotFoundException extends EntityNotFoundException{

    public BookNotFoundException() {
        super ("BOOK");
    }
}
