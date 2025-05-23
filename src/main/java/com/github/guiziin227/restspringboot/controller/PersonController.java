package com.github.guiziin227.restspringboot.controller;

import com.github.guiziin227.restspringboot.model.Person;
import com.github.guiziin227.restspringboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;


    @GetMapping(value = "/{id}",produces = "application/json")
    public Person findById(@PathVariable("id") String id) {
        return personService.findById(id);
    }
}
