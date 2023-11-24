package com.example.dataaccess;

import com.example.entities.Message;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Populator {

    MessageRepo repo;
    PersonRepo personRepo;

    @Autowired
    public Populator(MessageRepo repo, PersonRepo personRepo) {
        this.repo = repo;
        this.personRepo = personRepo;
    }

    //@EventListener(ContextRefreshedEvent.class)

    public void populateMessages() {

        Person esra = new Person("esra","esra@email.com");
        esra=personRepo.save(esra);

        Person sara = new Person("sara","sara@email.com");
        sara=personRepo.save(sara);

        Message message = new Message("This is a message",esra);
        this.repo.save(message);

        message = new Message("I love Java!!! (Coffee, not the language)",esra);
        this.repo.save(message);
    }

}


