package com.github.guiziin227.restspringboot.controller;

import com.github.guiziin227.restspringboot.dto.PersonDTO;
import com.github.guiziin227.restspringboot.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/person/v1")
@Tag(name = "People", description = "Endpoints for managing persons")
public class PersonController implements com.github.guiziin227.restspringboot.controller.docs.PersonControllerDocs {

    @Autowired
    private PersonService personService;

    //@CrossOrigin(origins = {"http://localhost:8080"})
    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    @Override
    public PersonDTO create(@RequestBody PersonDTO person) {
        return personService.create(person);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //@CrossOrigin(origins = {"http://localhost:8080"})
    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            })
    @Override
    public PersonDTO findById(@PathVariable("id") Long id) {
        //person.setBirthDate(new Date()); // apenas para simular uma data, pois o model não possui data de nascimento
        //person.setPhoneNumber("55991654341"); // apenas para simular um telefone, pois o model não possui telefone
        // person.setPhoneNumber("");
        //person.setLastName(null);
        //person.setSensitiveData("I am sensitive data, do not show me!");
        return personService.findById(id);
    }

    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            })
    @Override
    public List<PersonDTO> findAll() {
        return personService.findAll();
    }


    @PutMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            })
    @Override
    public PersonDTO update(@RequestBody PersonDTO person) {
        return personService.update(person);
    }

}
