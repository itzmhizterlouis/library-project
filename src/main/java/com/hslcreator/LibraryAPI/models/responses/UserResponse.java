package com.hslcreator.LibraryAPI.models.responses;


import com.hslcreator.LibraryAPI.models.entities.Department;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private String matricNumber;
    private int userId;
    private Department department;
}
