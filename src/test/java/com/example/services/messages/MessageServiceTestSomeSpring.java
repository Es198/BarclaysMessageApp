package com.example.services.messages;

import com.example.TestUtilities;
import com.example.dataaccess.MessageRepo;
import com.example.entities.Message;
import com.example.services.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest
class MessageServiceTestSomeSpring {

    @Autowired
    MessageService messageService;

    @MockBean
    MessageRepo mockRepo;

    @BeforeEach
    void beforeEach(){
        reset(mockRepo);
    }

    @Test
    void findAll() {
        List<Message> messages= TestUtilities.getMessageList();
        when(this.mockRepo.findAll()).thenReturn(messages);
        List<Message>actualMessages=messageService.findAll();
        Assertions.assertEquals(messages,actualMessages);
    }


    @Test
    void testGetMessageByIdOptionalEmpty(){
        long messageId=1;
        Optional<Message> optionalMessage=Optional.empty();
        when(this.mockRepo.findById(messageId)).thenReturn(optionalMessage);
        Message actual= messageService.getMessageById(messageId);
        Assertions.assertNull(actual);

    }

    @Test
    void testGetMessageByIdOptionalNotEmpty(){
        //Message message=this.mockMessageRepo.save(new Message("heyyy"));
        Message message= new Message("heyy");
        when(this.mockRepo.findById(any())).thenReturn(Optional.of(message));

        Message actual= messageService.getMessageById(1L);

        Assertions.assertEquals(message.getContent(),actual.getContent());
        Assertions.assertEquals(message.getId(),actual.getId());

        System.out.println(message);

    }


}