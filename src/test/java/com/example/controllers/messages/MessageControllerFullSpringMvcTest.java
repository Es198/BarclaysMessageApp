package com.example.controllers.messages;


import com.example.controllers.MessageController;
import com.example.entities.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.example.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
class MessageControllerFullSpringMvcTest {
    //still doing dependancy injection
    @MockBean
    MessageService mockMessageService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper=new ObjectMapper();

    @Test
    void testServiceCalledFor_getAllMessages() throws Exception {
        MockHttpServletRequestBuilder requestBuilder =  MockMvcRequestBuilders.get("/messages");
        mockMvc.perform(requestBuilder);
        verify(mockMessageService, times(1)).findAll();
    }

    @Test
    void testGetMessageById() throws Exception {
        long messageId=1;
        Message message=new Message("This is a message");
        when(mockMessageService.getMessageById(messageId)).thenReturn(message);

        MockHttpServletRequestBuilder requestBuilder= MockMvcRequestBuilders.get("/messages/"+messageId);
        MvcResult result=mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        verify(mockMessageService, times(1)).getMessageById(messageId);

//basically, the problem with this is that the reference ID wgenerated by ide was the same as the message ID given in the real world. beacuse the id goes out in text and brings it to the application.
//but this should not be the case, they should be totally different in real life.

    }

    //we want to test status code is correct:
    @Test
    void testGetMessageByIdBadIndex() throws Exception {
        long messageId = 0;

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/messages/" + messageId);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();




    }



}