package com.hslcreator.LibraryAPI.models.requests;


import lombok.Data;

@Data
public class BookSearchRequest {

    private String bookName;
    private String author;
}
