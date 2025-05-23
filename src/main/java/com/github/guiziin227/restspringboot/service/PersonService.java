package com.github.guiziin227.restspringboot.service;

import com.github.guiziin227.restspringboot.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public Person create(Person person) {
        logger.info("Creating one person!");
        return person;
    }

    public List<Person> findAll() {
        logger.info("findAll people!");
        List<Person> persons = new ArrayList<>();

        for(int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        }

        return persons;
    }


    public Person findById(String id) {
        logger.info("Finding one person!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Guilherme");
        person.setLastName("Henriques");
        person.setAddress("Santa Maria - RS");
        person.setGender("MASCULINO");
        return person;
    }


    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("FirstName " + i);
        person.setLastName("LastName " + i);
        person.setAddress("Santa Maria - RS");
        person.setGender("MASCULINO");

        return person;
    }

}
