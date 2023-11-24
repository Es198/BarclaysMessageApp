package com.example.controllers.person;
import com.example.controllers.PersonController;
import com.example.entities.Person;
import com.example.services.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PersonController.class)
public class PersonControllerFullSpringMvcTest {
    @MockBean
    PersonService mockPersonService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonController personController;

    ObjectMapper mapper =new ObjectMapper();

    @Test
    void findAll() throws Exception {
        MockHttpServletRequestBuilder requestBuilder =  MockMvcRequestBuilders.get("/persons");
        mockMvc.perform(requestBuilder);
        verify(mockPersonService, times(1)).findAll();
    }

    void addPerson() throws Exception {
        Person person = new Person("esra","esra@email.com");
        String json= mapper.writeValueAsString(person);
        MvcResult result= (this.mockMvc.perform(MockMvcRequestBuilders.get("/persons")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(json))
                .andReturn();
    }
}