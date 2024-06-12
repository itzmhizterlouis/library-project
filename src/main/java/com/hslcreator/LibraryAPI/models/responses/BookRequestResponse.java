package com.hslcreator.LibraryAPI.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequestResponse {

    private String message;
    private int bookRequestId;
}
