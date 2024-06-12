package com.hslcreator.LibraryAPI.models.entities;


import com.hslcreator.LibraryAPI.models.responses.BookResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    private String author;
    private String name;
    private String about;
    private String imageUrl;

    public BookResponse toDto() {

        return BookResponse.builder()
                .name(name)
                .author(author)
                .imageUrl(imageUrl)
                .about(about)
                .build();
    }
}
