package com.example.dataaccess;

import com.example.entities.Message;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//what we take in (messages and the key which is of type Long)
public interface MessageRepo extends ListCrudRepository<Message,Long>{
    List<Message> findMessagesBySenderEmail(String email);

}
