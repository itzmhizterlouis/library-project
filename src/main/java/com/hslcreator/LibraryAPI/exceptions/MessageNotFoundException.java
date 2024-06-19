package com.hslcreator.LibraryAPI.exceptions;

public class MessageNotFoundException extends EntityNotFoundException{
    public MessageNotFoundException() {
        super("MESSAGE");
    }
}
