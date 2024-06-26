package com.hslcreator.LibraryAPI.models.responses;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {

    private String author;
    private String about;
    private String name;
    private int bookId;
}
