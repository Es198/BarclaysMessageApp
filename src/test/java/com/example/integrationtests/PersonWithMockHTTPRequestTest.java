package com.example.integrationtests;

import com.example.entities.Person;
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

public class PersonWithMockHTTPRequestTest {
    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper mapper=new ObjectMapper();

    public static final String EXPECTED_JSON = """
            [{"id":100,"name":"esra"},{"id":200,"name":"sara"},{"id":300,"name":"emily"},{"id":400,"name":"jill"}]""";


    @Test
    public void getPersonTest() throws Exception{
        MvcResult result= (this.mockMvc.perform(MockMvcRequestBuilders.get("/persons")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String contentAsJson = result.getResponse().getContentAsString();
        Person[] actualPerson = mapper.readValue(contentAsJson,Person[].class);

        assertEquals("esra",actualPerson[0].getName());
        assertEquals("sara",actualPerson[1].getName());
        assertEquals("emily",actualPerson[2].getName());
        assertEquals("jill",actualPerson[3].getName());


    }


    @Test
    public void testGetPersonById() throws Exception {
        long personId = 100;

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/persons/" + personId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        String contentAsJson = result.getResponse().getContentAsString();
        Person actualPerson = mapper.readValue(contentAsJson, Person.class);
        assertEquals("esra", actualPerson.getName());
    }





    }

