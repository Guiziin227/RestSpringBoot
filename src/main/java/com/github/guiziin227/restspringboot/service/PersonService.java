package com.github.guiziin227.restspringboot.service;

import com.github.guiziin227.restspringboot.exception.ResourceNotFoundException;
import com.github.guiziin227.restspringboot.model.Person;
import com.github.guiziin227.restspringboot.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private final PersonRepository personRepository;


    public Person create(Person person) {
        logger.info("Creating one person!");
        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one person!");
        Person p =  personRepository.findById(person.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Person not found!")
        );

        return personRepository.save(p);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        personRepository.deleteById(id);
    }

    public List<Person> findAll() {
        logger.info("findAll people!");
        return personRepository.findAll();
    }


    public Person findById(Long id) {
        logger.info("Finding one person!");
        return personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Person not found!")
        );
    }

}
