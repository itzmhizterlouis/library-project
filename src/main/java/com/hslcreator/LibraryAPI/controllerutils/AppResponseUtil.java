package com.hslcreator.LibraryAPI.controllerutils;

import com.hslcreator.LibraryAPI.utils.AppResponse;

public class AppResponseUtil {
    public static AppResponse buildErrorResponse(String message) {
        return new AppResponse(false, message);
    }
}
