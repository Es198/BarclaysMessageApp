package com.example.integrationtests;

import com.example.entities.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonWithRealHttpRequestTest {

    ObjectMapper mapper = new ObjectMapper();

    @Disabled
    @Test

    public void testGettingAllMessages() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/persons");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Person[] person = mapper.readValue(response.getEntity().getContent(), Person[].class);
        assertEquals("esra", person[0].getName());
        assertEquals("sara", person[1].getName());
        assertEquals("emily", person[2].getName());
        assertEquals("jill", person[3].getName());

    }
}

