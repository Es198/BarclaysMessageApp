package com.example.controllers.person;

import com.example.controllers.PersonController;
import com.example.entities.Person;
import com.example.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PersonControllerNoSpringTest {
    PersonController personController;
    PersonService mockPersonService;

    @BeforeEach
    void beforeEach(){
         this.mockPersonService = mock(PersonService.class);
         this.personController = new PersonController(mockPersonService);
    }
    @Test
    void findAllPeople() {
        this.personController.findAll();
        verify(this.mockPersonService,times(1)).findAll();
    }
    @Test
    void testGetPersonByID(){
        long personID=1;
        when(this.mockPersonService.getPersonById(personID)).thenReturn(new Person("sally","sally@email.com"));
        this.personController.getPersonById(personID);
        verify(this.mockPersonService,times(1)).getPersonById(personID);
    }

}