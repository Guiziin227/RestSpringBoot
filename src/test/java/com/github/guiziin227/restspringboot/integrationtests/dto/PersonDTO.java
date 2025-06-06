package com.github.guiziin227.restspringboot.integrationtests.dto;

import java.io.Serializable;
import java.util.Objects;

public class PersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String firstName;
    private String lastName;
    private String address;

    private String gender;
    private Boolean enabled;

    public PersonDTO() {
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        com.github.guiziin227.restspringboot.dto.PersonDTO personDTO = (com.github.guiziin227.restspringboot.dto.PersonDTO) o;
        return Objects.equals(id, personDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

