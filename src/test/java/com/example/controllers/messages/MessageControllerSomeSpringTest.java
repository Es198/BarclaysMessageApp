package com.example.controllers.messages;

import com.example.controllers.MessageController;
import com.example.entities.Message;
import com.example.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class MessageControllerSomeSpringTest {
    //using spring for dependancy injection.
//this test is a bean.
    @Autowired
    MessageController messageController;
    //instead of populating container with a bean, mock the bean
    //everytime someone asks fr message service, give them the mock instead
    @MockBean
    MessageService mockMessageService;

    @Test
    public void getObjectsFromContextAndTestBehavior() {
        List<Message> messages = messageController.findAll();
        verify(mockMessageService, times(1)).findAll();
    }
}