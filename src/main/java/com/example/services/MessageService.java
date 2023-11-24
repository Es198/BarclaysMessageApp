package com.example.services;

import com.example.dataaccess.MessageRepo;
import com.example.dataaccess.PersonRepo;
import com.example.entities.Message;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    public static final String MESSAGE_ID_MUST_BE_NULL_OR_0 = "The message id provided for a create/post must be null or zero.";
    public static final String SENDER_MUST_EXIST = "The sender of the message must already exist.";
    MessageRepo messageRepo;
    PersonRepo personRepo;

    @Autowired
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public List<Message> findAll() {
        return this.messageRepo.findAll();
    }

    public Message getMessageById(long messageId) {
        Optional<Message> message = this.messageRepo.findById(messageId);
        return message.orElse(null);
    }

    public List<Message> getMessageBySenderEmail(String email) {
        return messageRepo.findMessagesBySenderEmail(email);
    }

    public Message addMessage(Message message) {
        // If the user provided an ID for the message
        if (message.getId() != null && message.getId() != 0) {
            throw new IllegalArgumentException(MESSAGE_ID_MUST_BE_NULL_OR_0);
        }
        // If the sender ID isn't set to a valid number(if they give us null or 0 we explode)
        Person sender = message.getSender();

        if (sender.getId() == null || message.getId() == 0){
            throw new IllegalArgumentException(SENDER_MUST_EXIST);}

        // If the user provided a valid integer for ID, but it's not in the database
        if (!this.personRepo.existsById(sender.getId())) {
            throw new IllegalArgumentException(SENDER_MUST_EXIST);
        }

        // If we've gotten here, it's safe to attempt to save the message
        return this.messageRepo.save(message);
    }

}