package com.hslcreator.LibraryAPI.utils;

public class AppResponseUtil {
    public static AppResponse buildErrorResponse(String message) {
        return new AppResponse(false, message);
    }
}
