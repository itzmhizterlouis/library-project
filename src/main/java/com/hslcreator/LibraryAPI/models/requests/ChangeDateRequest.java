package com.hslcreator.LibraryAPI.models.requests;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChangeDateRequest {

    private Timestamp newDueDate;
}
