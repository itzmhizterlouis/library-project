package com.hslcreator.LibraryAPI.services;


import com.hslcreator.LibraryAPI.exceptions.MessageNotFoundException;
import com.hslcreator.LibraryAPI.models.entities.Message;
import com.hslcreator.LibraryAPI.models.responses.MessageResponse;
import com.hslcreator.LibraryAPI.repositories.MessageRepository;
import com.hslcreator.LibraryAPI.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public List<MessageResponse> getMessageForUser() {

        return messageRepository.findAllByUserIdOrderByCreatedAtDesc(UserUtil.getLoggedInUser().get().getUserId())
                .parallelStream().map(Message::toDto).toList();
    }

    public MessageResponse getMessageByMessageId(int messageId) {

        return messageRepository.findByMessageId(messageId).orElseThrow(MessageNotFoundException::new).toDto();
    }
}
