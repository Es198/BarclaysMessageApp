package com.example.services.person;


import com.example.TestUtilitiesPerson;
import com.example.controllers.PersonController;
import com.example.dataaccess.PersonRepo;
import com.example.entities.Person;
import com.example.services.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceSomeSpring {


    @Autowired
    PersonService personServiceImpl;

    @MockBean
    PersonRepo mockPersonRepo;

    @Autowired
    private PersonController personService;

    @Test
    void findAll() {
        List<Person> person= TestUtilitiesPerson.getPersonList();
        when(this.mockPersonRepo.findAll()).thenReturn(person);
        List<Person>actualPerson=personServiceImpl.findAll();
        Assertions.assertEquals(person,actualPerson);
    }

    @Test
    void testGetPersonByIdOptionalEmpty() {
        long personId = 1;
        Optional<Person> optionalPerson = Optional.empty();
        when(this.mockPersonRepo.findById(personId)).thenReturn(optionalPerson);
        Person actual = personServiceImpl.getPersonById(personId);
        Assertions.assertNull(actual);

    }

    @Test
    void testGetPersonByIdOptionalNotEmpty() {
        Person person = new Person("esra","esra@email.com");
        when(this.mockPersonRepo.findById(any())).thenReturn(Optional.of(person));

        Person actual = personServiceImpl.getPersonById(1L);

        verify(mockPersonRepo, times(1)).findById(any());

        Assertions.assertEquals(person.getName(), actual.getName());
        //Assertions.assertEquals(person.getId(), actual.getId());
    }//TODO: FIX COMMENT

}
