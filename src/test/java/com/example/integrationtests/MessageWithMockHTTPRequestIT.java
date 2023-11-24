package com.example.integrationtests;

import com.example.entities.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:test-data.sql")
@SpringBootTest
@DirtiesContext(classMode=DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.sql.init.mode=never"})



public class MessageWithMockHTTPRequestIT {

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper mapper=new ObjectMapper();


    @Test
    //im calling the method that comes out of this method
    public void testGettingAllMessages() throws Exception {

        MvcResult result=
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/messages")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
//this method below works, its just another method.
        String contentJson = result.getResponse().getContentAsString();
        Message[] actualMessages=mapper.readValue(contentJson,Message[].class);

        assertEquals("hey",actualMessages[0].getContent());
        assertEquals("hey",actualMessages[1].getContent());
        assertEquals("hey",actualMessages[2].getContent());
        assertEquals("hey",actualMessages[3].getContent());
    }

    @Test
    public void testFindMessageBySenderEmail() throws Exception {
        String senderEmail="esra@email.com";
        MvcResult result=
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/messages/sender/email/"+senderEmail)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String contentAsJson=result.getResponse().getContentAsString();
        Message[] messages=mapper.readValue(contentAsJson,Message[].class);
        assertEquals(10, messages[0].getId());
    }
}