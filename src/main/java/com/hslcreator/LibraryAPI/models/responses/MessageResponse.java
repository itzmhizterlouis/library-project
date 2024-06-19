package com.hslcreator.LibraryAPI.models.responses;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {

    private int messageId;
    private int adminId;
    private int userId;
    private String message;
    private int bookId;
}
