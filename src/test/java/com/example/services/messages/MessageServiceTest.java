package com.example.services.messages;

import com.example.TestUtilities;
import com.example.dataaccess.MessageRepo;
import com.example.entities.Message;
import com.example.services.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class MessageServiceTest {
    MessageRepo mockMessageRepo=mock(MessageRepo.class);
    MessageService messageService=new MessageService(mockMessageRepo);

@BeforeEach
void beforeEach(){

}
    @Test
    void findAll() {
        List<Message>messages= TestUtilities.getMessageList();
        when(mockMessageRepo.findAll()).thenReturn(messages);
        List<Message>actualMessages=messageService.findAll();
        Assertions.assertEquals(messages,actualMessages);

    }

    @Test
    void testRepoCalled(){
    List<Message> actualMessage=messageService.findAll();
    verify(mockMessageRepo,times(1)).findAll();

    }

    @Test
    void testGetMessageByIdOptionalEmpty(){
    long messageId=1;
        Optional<Message> optionalMessage=Optional.empty();
        when(this.mockMessageRepo.findById(messageId)).thenReturn(optionalMessage);
        Message actual= messageService.getMessageById(messageId);
        Assertions.assertNull(actual);

    }

    @Test
    void testGetMessageByIdOptionalNotEmpty(){
    //Message message=this.mockMessageRepo.save(new Message("heyyy"));
    Message message= new Message("heyy");
    when(this.mockMessageRepo.findById(any())).thenReturn(Optional.of(message));

    Message actual= messageService.getMessageById(1L);

    verify(mockMessageRepo,times(1)).findById(any());

    Assertions.assertEquals(message.getContent(),actual.getContent());
    Assertions.assertEquals(message.getId(),actual.getId());

    //System.out.println(message);




    }
}