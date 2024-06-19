package com.hslcreator.LibraryAPI.models.entities;


import com.hslcreator.LibraryAPI.models.responses.MessageResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;
    private int adminId;
    private int userId;
    private int bookId;
    private String message;
    private Instant createdAt;

    public MessageResponse toDto() {
        return MessageResponse.builder()
                .messageId(messageId)
                .userId(userId)
                .adminId(adminId)
                .message(message)
                .bookId(bookId)
                .build();
    }
}
