package com.hslcreator.LibraryAPI.models.requests;

import com.hslcreator.LibraryAPI.models.entities.Department;
import com.hslcreator.LibraryAPI.models.entities.Role;
import lombok.Data;


@Data
public class SignupRequest {

    private String matricNumber;
    private String password;
    private Role role;
    private Department department;
}
