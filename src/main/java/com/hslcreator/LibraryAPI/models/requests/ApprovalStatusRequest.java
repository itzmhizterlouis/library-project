package com.hslcreator.LibraryAPI.models.requests;


import com.hslcreator.LibraryAPI.models.entities.ApprovalStatus;
import lombok.Data;

@Data
public class ApprovalStatusRequest {

    private ApprovalStatus approvalStatus;
}
