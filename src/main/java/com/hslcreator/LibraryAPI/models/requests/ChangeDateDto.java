package com.hslcreator.LibraryAPI.models.requests;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChangeDateDto {

    private Timestamp newDueDate;
}
