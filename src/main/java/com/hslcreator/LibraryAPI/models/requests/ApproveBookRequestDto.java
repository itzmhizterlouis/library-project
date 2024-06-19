package com.hslcreator.LibraryAPI.models.requests;


import com.hslcreator.LibraryAPI.models.entities.ApprovalStatus;
import lombok.Data;

@Data
public class ApproveBookRequestDto {

    private ApprovalStatus approvalStatus;
    private String message;
}
