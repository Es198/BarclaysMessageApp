package com.example.services.person;


import com.example.TestUtilitiesPerson;
import com.example.dataaccess.PersonRepo;
import com.example.entities.Person;
import com.example.services.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class PersonServiceNoSpring {

    PersonRepo mockPersonRepo = mock(PersonRepo.class);
    PersonService personService = new PersonService(mockPersonRepo);

    @BeforeEach
    void beforeEach() {
    }

    @Test
    void findAll() {
        List<Person> person = TestUtilitiesPerson.getPersonList();
        when(mockPersonRepo.findAll()).thenReturn(person);
        List<Person> actualPerson = personService.findAll();
        Assertions.assertEquals(person, actualPerson);
    }

    @Test
    void testRepoCalled() {
        List<Person> actualPerson = personService.findAll();
        verify(mockPersonRepo, times(1)).findAll();
    }

    @Test
    void testGetPersonByIdOptionalEmpty() {
        long personId = 1;
        Optional<Person> optionalPerson = Optional.empty();
        when(this.mockPersonRepo.findById(personId)).thenReturn(optionalPerson);
        Person actual = personService.getPersonById(personId);
        Assertions.assertNull(actual);

    }

    @Test
    void testGetPersonByIdOptionalNotEmpty() {
        Person person = new Person("esra","esra@email.com");
        when(this.mockPersonRepo.findById(any())).thenReturn(Optional.of(person));

        Person actual = personService.getPersonById(1L);

        verify(mockPersonRepo, times(1)).findById(any());

        Assertions.assertEquals(person.getName(), actual.getName());
       // Assertions.assertEquals(person.getId(), actual.getId());
    }//TODO CORRECT THIS COMMENT
}
