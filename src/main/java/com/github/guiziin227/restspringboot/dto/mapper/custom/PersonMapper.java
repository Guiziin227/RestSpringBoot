package com.github.guiziin227.restspringboot.dto.mapper.custom;

import com.github.guiziin227.restspringboot.dto.v2.PersonDTOV2;
import com.github.guiziin227.restspringboot.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {
    //converter entidade para DTO
    public PersonDTOV2 convertEntityToDTO(Person person) {
        PersonDTOV2 dto = new PersonDTOV2();
        dto.setAddress(person.getAddress());
        dto.setFirstName(person.getFirstName());
        dto.setBirthDate(new Date());
        dto.setLastName(person.getLastName());
        dto.setGender(person.getGender());

        return dto;
    }

    //converter DTO para entidade
    public Person convertDTOToEntity(PersonDTOV2 dto) {
        Person person = new Person();
        person.setAddress(dto.getAddress());
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setGender(dto.getGender());

        return person;
    }

}
