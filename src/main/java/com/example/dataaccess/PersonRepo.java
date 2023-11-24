package com.example.dataaccess;


import com.example.entities.Person;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends ListCrudRepository<Person,Long> {

}
