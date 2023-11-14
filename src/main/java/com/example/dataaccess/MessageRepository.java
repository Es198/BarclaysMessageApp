package com.example.dataaccess;

import com.example.entities.Message;
import org.springframework.data.repository.ListCrudRepository;

public interface MessageRepository extends ListCrudRepository<Message, Long> {
}