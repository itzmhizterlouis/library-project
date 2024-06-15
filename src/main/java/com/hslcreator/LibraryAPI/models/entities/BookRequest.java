package com.hslcreator.LibraryAPI.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_requests")
public class BookRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookRequestId;
    private int userId;
    private Instant pickUpDate;
    private Instant dueDate;
    private int bookId;

    private String description;

    @Enumerated(EnumType.STRING)
    private BookRequestType bookRequestType;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    private Instant createdAt;
}
