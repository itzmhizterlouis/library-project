package com.hslcreator.LibraryAPI.models.requests;


import lombok.Data;

@Data
public class LoginRequest {

    private String matricNumber;
    private String password;
}
