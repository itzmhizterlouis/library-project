package com.hslcreator.LibraryAPI.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppResponse {

    public AppResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    private boolean status;
    private String message;
    private Object data;
}
