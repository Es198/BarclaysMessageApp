package com.example.integrationtests;

import com.example.dataaccess.PersonRepo;
import com.example.entities.Message;
import com.example.entities.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@Sql("classpath:test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})

@Disabled
public class AddMessageWithMockHttpRequestIT {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    PersonRepo personRepo;

    Person existingPerson;
    Person newPerson;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        if(existingPerson == null)
            existingPerson = new Person("Existing Person", "iexist@universe.com");

        // Ensure this Person object has an ID
        if(existingPerson.getId() == null)
            this.personRepo.save(existingPerson);

        // Will not have an ID
        newPerson = new Person("New Person", "noob@noob.com");
    }


    static final String NEW_MESSAGE_CONTENT = "This is the content of a new message";
    @Disabled
    @Test
    void testAddMessageHappyPath() throws Exception {
        Message newMessage = new Message(NEW_MESSAGE_CONTENT, this.existingPerson);
        String json = this.objectMapper.writeValueAsString(newMessage);

        MvcResult result =
                this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        checkMessageFields(result, newMessage);
    }
    @Disabled
    @Test
    void testAddMessageIllegalMessageId() throws Exception {
        Message newMessage = new Message(NEW_MESSAGE_CONTENT, this.existingPerson);
        String json = getMessageJson(newMessage, 5L, newMessage.getSender().getId());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Disabled
    @Test
    void testAddMessageIllegalSenderId() throws Exception {
        Message newMessage = new Message(NEW_MESSAGE_CONTENT, newPerson);
        assertNull(newPerson.getId());  // Sanity check

        String json = getMessageJson(newMessage, newMessage.getId(), newMessage.getSender().getId());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Disabled
    @Test
    void testAddMessageWithNoSenderId() throws Exception {
        String json =
                """
                {
                  "id": %d,
                  "sender": {
                    "name": "%s",
                    "email": "%s"
                  },
                  "content": "Josh's first message."
                }""".formatted(0, existingPerson.getName(), existingPerson.getEmail());

        Message expectedMessage = this.objectMapper.readValue(json, Message.class);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Disabled
    @Test
    void testAddMessageHappyPathWithNoMessageId() throws Exception {
        String json =
                """
                {
                  "sender": {
                    "id": %d,
                    "name": "%s",
                    "email": "%s"
                  },
                  "content": "Josh's first message."
                }""".formatted(existingPerson.getId(), existingPerson.getName(), existingPerson.getEmail());

        Message expectedMessage = this.objectMapper.readValue(json, Message.class);

        MvcResult result =
                this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        checkMessageFields(result, expectedMessage);
    }
    @Disabled
    private void checkMessageFields(MvcResult result, Message expectedMessage) throws UnsupportedEncodingException, JsonProcessingException {
        String resultJson = result.getResponse().getContentAsString();
        Message resultMessage = this.objectMapper.readValue(resultJson, Message.class);

        assertEquals(expectedMessage.getSender().getName(), resultMessage.getSender().getName());
        assertEquals(expectedMessage.getSender().getEmail(), resultMessage.getSender().getEmail());
        assertEquals(expectedMessage.getContent(), resultMessage.getContent());

        Assertions.assertNotNull(resultMessage.getId());
        Assertions.assertTrue(resultMessage.getId() > 0);

        Assertions.assertNotNull(resultMessage.getSender().getId());
        Assertions.assertTrue(resultMessage.getSender().getId() > 0);
    }

    @Disabled
    private String getMessageJson(Message message, Long messageId, Long senderId) {
        Person sender = message.getSender();
        return """
                {
                  "id": %d,
                  "sender": {
                    "id": %d,
                    "name": "%s",
                    "email": "%s"
                  },
                  "content": "%s"
                }""".formatted(messageId, senderId, sender.getName(), sender.getEmail(), message.getContent());
    }
}













