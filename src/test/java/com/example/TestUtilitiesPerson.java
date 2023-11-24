package com.example;

import com.example.entities.Person;

import java.util.ArrayList;
import java.util.stream.StreamSupport;

public class TestUtilitiesPerson {
    public static ArrayList<Person> getPersonList(){
        ArrayList<Person> person=new ArrayList<>();
        person.add(new Person("esra","esra@email.com"));
        person.add(new Person("sara","sara@email.com"));
        person.add(new Person("emily","emily@email.com"));
        person.add(new Person("jill","jill@email.com"));
        return person;
    }

    public static <T> long getSize(Iterable<T> iterable){
        return StreamSupport.stream(iterable.spliterator(),false).count();
    }
}


