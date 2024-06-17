package com.hslcreator.LibraryAPI.models.responses;

import com.hslcreator.LibraryAPI.models.entities.Department;
import com.hslcreator.LibraryAPI.models.entities.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private int userId;
    private String token;
    private Role role;
    private Department department;
}
