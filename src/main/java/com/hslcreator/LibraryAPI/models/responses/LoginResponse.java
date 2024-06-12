package com.hslcreator.LibraryAPI.models.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private int userId;
    private String token;
}
