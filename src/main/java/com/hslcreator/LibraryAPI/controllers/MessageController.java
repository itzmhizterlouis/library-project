package com.hslcreator.LibraryAPI.controllers;


import com.hslcreator.LibraryAPI.models.responses.MessageResponse;
import com.hslcreator.LibraryAPI.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Notification for getting all user messages", description = "Gets the message whether a book request has been approved or denied")
    @GetMapping
    public List<MessageResponse> getMessagesForUser() {

        return messageService.getMessageForUser();
    }

    @Operation(summary = "Get Message By Message Id", description = "Get the specific message to show the details of the message itself indepth")
    @GetMapping("{messageId}")
    public MessageResponse getMessageByMessageId(@PathVariable int messageId) {

        return messageService.getMessageByMessageId(messageId);
    }
}
