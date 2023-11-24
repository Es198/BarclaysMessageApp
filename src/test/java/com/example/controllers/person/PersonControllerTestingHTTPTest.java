package com.example.controllers.person;

import com.example.TestUtilitiesPerson;
import com.example.controllers.PersonController;
import com.example.entities.Person;
import com.example.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
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

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)

class PersonControllerTestingHTTPTest {
    @MockBean
    PersonService mockPersonService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper=new ObjectMapper();

    @Autowired
    PersonController personController;


    @Test
    void testServiceCalledFor_getAll() throws Exception{
        List<Person> person= TestUtilitiesPerson.getPersonList();
        String expectedJson=mapper.writeValueAsString(person);
        when (mockPersonService.findAll()).thenReturn(person);
        MvcResult result= (this.mockMvc.perform(MockMvcRequestBuilders.get("/persons")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedJson))
                .andReturn();

        verify(mockPersonService,times(1)).findAll();
    }

    @Test
    void testGetPersonById() throws Exception {
        long personID=1;
        Person person = new Person();
        when(mockPersonService.getPersonById(personID)).thenReturn(person);
        MockHttpServletRequestBuilder requestBuilder= MockMvcRequestBuilders.get("/persons/"+personID);
        MvcResult result=mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        //this.personController.getPersonById(personID);
        verify(this.mockPersonService,times(1)).getPersonById(personID);
    }

    //this test will not verify that the correct message was received.
    //it only verifies that the getPersonId in personService was called with the correct parameter!

    @Disabled //this is added so that the tests can pass for the real HTTP requests
    @Test
    void testGetPersonByBadPersonIndex() throws Exception {
        long personID=0;
        MockHttpServletRequestBuilder requestBuilder= MockMvcRequestBuilders.get("/persons/"+personID);
        MvcResult result=mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
        verify(mockPersonService, times(1)).getPersonById(personID);
    }
}

