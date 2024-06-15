package com.hslcreator.LibraryAPI.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "change_date_requests")
public class ChangeDateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int changeDateRequestId;

    private int bookRequestId;
    private int userId;

    private Instant newDueDate;
    private Instant oldDueDate;
}
