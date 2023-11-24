package com.example.controllers.person;

import com.example.controllers.PersonController;
import com.example.entities.Person;
import com.example.services.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PersonControllerSomeSpringTest {

        @MockBean
        PersonService mockPersonService;

        @Autowired
        PersonController uut;

        @Test
        void testGetAllPersons() {
            //Iterable<Person> Person = getAllPersons();
            Iterable<Person> person = uut.findAll();
            verify(mockPersonService, times(1)).findAll();
        }
    }