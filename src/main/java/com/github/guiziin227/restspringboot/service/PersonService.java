package com.github.guiziin227.restspringboot.service;

import com.github.guiziin227.restspringboot.controller.PersonController;
import com.github.guiziin227.restspringboot.dto.mapper.custom.PersonMapper;
import com.github.guiziin227.restspringboot.dto.PersonDTO;
import static com.github.guiziin227.restspringboot.dto.mapper.ObjectMapper.parseListObjects;
import static com.github.guiziin227.restspringboot.dto.mapper.ObjectMapper.parseObject;

import com.github.guiziin227.restspringboot.exception.ResourceNotFoundException;
import com.github.guiziin227.restspringboot.model.Person;
import com.github.guiziin227.restspringboot.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        Person entity = parseObject(person, Person.class);

        PersonDTO dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
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

        PersonDTO dto = parseObject(personRepository.save(p), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        personRepository.deleteById(id);

    }

    public List<PersonDTO> findAll() {
        logger.info("findAll people!");
        List<PersonDTO> dto = parseListObjects(personRepository.findAll(), PersonDTO.class);
        for (PersonDTO personDTO : dto) {
            addHateoasLinks(personDTO);
        }
        return dto;
    }


    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");
        Person entity = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Person not found!")
        );
        PersonDTO dto =  parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    private static void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }

}
