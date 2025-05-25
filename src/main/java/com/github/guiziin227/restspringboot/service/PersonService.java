package com.github.guiziin227.restspringboot.service;

import com.github.guiziin227.restspringboot.dto.mapper.custom.PersonMapper;
import com.github.guiziin227.restspringboot.dto.PersonDTO;
import static com.github.guiziin227.restspringboot.dto.mapper.ObjectMapper.parseListObjects;
import static com.github.guiziin227.restspringboot.dto.mapper.ObjectMapper.parseObject;

import com.github.guiziin227.restspringboot.dto.v2.PersonDTOV2;
import com.github.guiziin227.restspringboot.exception.ResourceNotFoundException;
import com.github.guiziin227.restspringboot.model.Person;
import com.github.guiziin227.restspringboot.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PersonService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private final PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;


    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one person!");
        var entity = parseObject(person, Person.class);
        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating one person!");
        Person p =  personRepository.findById(person.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Person not found!")
        );

        p.setAddress(person.getAddress());
        p.setFirstName(person.getFirstName());
        p.setLastName(person.getLastName());
        p.setGender(person.getGender());

        return parseObject(personRepository.save(p), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        personRepository.deleteById(id);
    }

    public List<PersonDTO> findAll() {
        logger.info("findAll people!");
        return parseListObjects(personRepository.findAll(), PersonDTO.class);
    }


    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");
        var entity = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Person not found!")
        );
        return parseObject(entity, PersonDTO.class);
    }

}
