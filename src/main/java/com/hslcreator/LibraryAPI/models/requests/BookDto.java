package com.hslcreator.LibraryAPI.models.requests;


import com.hslcreator.LibraryAPI.models.entities.Department;
import lombok.Data;

import java.util.List;

@Data
public class BookDto {

    private String name;
    private String about;
    private String author;
    private List<Department> departments;
}
