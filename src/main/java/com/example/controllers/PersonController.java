package com.example.controllers;

import com.example.entities.Person;
import com.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class PersonController {

    PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person addPerson(@RequestBody Person person) {
        Person newPerson;
        try {
            newPerson = this.personService.addPerson(person);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return newPerson;
    }



    @GetMapping("/persons")
    public Iterable<Person> findAll() {
        return personService.findAll();
    }

    //Extending the task to get the person by ID
    @GetMapping("/persons/{personId}")
    public Person getPersonById(@PathVariable long personId) {
        Person person = personService.getPersonById(personId);
        if(person == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        return  person;
    }

}


