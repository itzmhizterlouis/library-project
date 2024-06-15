package com.hslcreator.LibraryAPI.models.responses;

import com.hslcreator.LibraryAPI.models.entities.ApprovalStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequestResponse {

    private String message;
    private int bookRequestId;
    private ApprovalStatus approvalStatus;
}
