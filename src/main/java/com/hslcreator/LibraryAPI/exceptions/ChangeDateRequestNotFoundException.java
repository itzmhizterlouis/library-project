package com.hslcreator.LibraryAPI.exceptions;

public class ChangeDateRequestNotFoundException extends EntityNotFoundException{
    public ChangeDateRequestNotFoundException() {
        super("REQUEST TO CHANGE DATE");
    }
}
