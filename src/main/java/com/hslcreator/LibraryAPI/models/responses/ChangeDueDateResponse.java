package com.hslcreator.LibraryAPI.models.responses;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ChangeDueDateResponse {

    private String matricNumber;
    private int bookRequestId;
    private Instant dueDate;
}
