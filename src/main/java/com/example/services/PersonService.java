package com.example.services;

import com.example.dataaccess.PersonRepo;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService{

    PersonRepo personRepo;

    @Autowired
    public PersonService(PersonRepo mockPersonRepo) {
        this.personRepo=mockPersonRepo;
    }

    public Person addPerson(Person person){
        if(person.getId() != null && person.getId() != 0)
            throw  new IllegalArgumentException("The id provided for a create/post must be null or zero.");
        return this.personRepo.save(person);
    }


    public List<Person> findAll() {
        return this.personRepo.findAll();
    }


    public Person getPersonById(long personId) {
        Optional<Person> optionalPerson=this.personRepo.findById(personId);
        if(optionalPerson.isEmpty()){
            return null;
        }else return optionalPerson.get();
    }

}



//list is an interface, and arraylist implements the interfecae
