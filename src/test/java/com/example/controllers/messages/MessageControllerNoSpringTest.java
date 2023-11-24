package com.example.controllers.messages;

import com.example.controllers.MessageController;
import com.example.entities.Message;
import com.example.services.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MessageControllerNoSpringTest {
    MessageService mockMessageService;
    MessageController messageController;

    @BeforeEach
    void beforeEach(){
        this.mockMessageService=mock(MessageService.class);
        this.messageController=new MessageController(mockMessageService);

    }

    @Test
    void getAllMessages() {
        //make fake service
        MessageService mockMessageService = mock(MessageService.class);

        //in order to create uut, constructor takes in message service so we use the mock verison.
        MessageController messageController = new MessageController(mockMessageService);

        List<Message> messages = messageController.findAll();
        //check if controller is called once
        this.messageController.findAll();
        verify(this.mockMessageService, times(1)).findAll();
    }
    //1st test
    @Test
    void testGetMessageById(){

        long messageId=1L;
        when(this.mockMessageService.getMessageById(messageId)).thenReturn(new Message("heyyy"));
        this.messageController.getMessageById(messageId);
        verify(this.mockMessageService,times(1)).getMessageById(messageId);
    }

    //TODO ADD THE EXCEPTION HERE
    @Test
    void testGetMessageByIdBadRequest(){
        when(mockMessageService.getMessageById(0)).thenReturn(null);
        assertThrows(ResponseStatusException.class,()->{
            this.messageController.getMessageById((0));


        });
    }
}