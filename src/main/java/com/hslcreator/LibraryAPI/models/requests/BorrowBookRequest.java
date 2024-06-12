package com.hslcreator.LibraryAPI.models.requests;

import com.hslcreator.LibraryAPI.models.entities.BookRequestType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;


@Data
@Builder
public class BorrowBookRequest {

    private Timestamp pickUpDate;
    private Timestamp dueDate;
    private String description;
    private BookRequestType bookRequestType;
}
