package com.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {

    @Id
    @GeneratedValue
    public Long id;
    private  String email;
    private String name;

    @OneToMany(mappedBy =  "sender")
    private List<Message> sentMessages=new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(String name, String email) {
        this.name = name;
        this.email=email;

    }

    public Long getId() {
        return id;
    }

    public Person(){}





}
