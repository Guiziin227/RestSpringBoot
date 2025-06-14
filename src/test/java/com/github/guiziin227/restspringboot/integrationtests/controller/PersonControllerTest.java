package com.github.guiziin227.restspringboot.integrationtests.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guiziin227.restspringboot.config.TestConfigs;
import com.github.guiziin227.restspringboot.integrationtests.dto.PersonDTO;
import com.github.guiziin227.restspringboot.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import com.github.guiziin227.restspringboot.integrationtests.dto.wrapper.PersonEmbeddedDTO;
import com.github.guiziin227.restspringboot.integrationtests.dto.wrapper.WrapperPersonDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static PersonDTO person;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonDTO();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockPerson();

        var response = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
            .when()
                .post()
            .then()
                .statusCode(200)
                .extract().body().asString();

        PersonDTO createdPerson = objectMapper.readValue(response, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Alina", createdPerson.getFirstName());
        assertEquals("Phettis", createdPerson.getLastName());
        assertEquals("7th Floors Main St", createdPerson.getAddress());
        assertEquals("Female", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }

    @Test
    @Order(2)
    void update() throws JsonProcessingException {
        person.setLastName("Bruh");

        var response = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract().body().asString();

        PersonDTO createdPerson = objectMapper.readValue(response, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Alina", createdPerson.getFirstName());
        assertEquals("Bruh", createdPerson.getLastName());
        assertEquals("7th Floors Main St", createdPerson.getAddress());
        assertEquals("Female", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }


    @Order(3)
    @Test
    void findById() throws JsonProcessingException {

        // Cria uma pessoa para este teste específico
        PersonDTO testPerson = new PersonDTO();
        mockPerson();
        testPerson = person;

        // Cria a pessoa no sistema
        var createResponse = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(testPerson)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().body().asString();

        testPerson = objectMapper.readValue(createResponse, PersonDTO.class);

        var response = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", testPerson.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract().body().asString();

        PersonDTO createdPerson = objectMapper.readValue(response, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Alina", createdPerson.getFirstName());
        assertEquals("Phettis", createdPerson.getLastName());
        assertEquals("7th Floors Main St", createdPerson.getAddress());
        assertEquals("Female", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }

    @Order(4)
    @Test
    void disablePerson() throws JsonProcessingException {

        // Cria uma pessoa para este teste específico
        PersonDTO testPerson = new PersonDTO();
        mockPerson();
        testPerson = person;

        // Cria a pessoa no sistema
        var createResponse = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(testPerson)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().body().asString();

        testPerson = objectMapper.readValue(createResponse, PersonDTO.class);

        // Agora desativa a pessoa com ID garantido
        var response = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", testPerson.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .extract().body().asString();

        PersonDTO createdPerson = objectMapper.readValue(response, PersonDTO.class);
        person = createdPerson;
        person.setEnabled(false);


        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Alina", createdPerson.getFirstName());
        assertEquals("Phettis", createdPerson.getLastName());
        assertEquals("7th Floors Main St", createdPerson.getAddress());
        assertEquals("Female", createdPerson.getGender());
        assertFalse(createdPerson.getEnabled());

    }

    @Order(5)
    @Test
    void delete() throws JsonProcessingException {

         given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    void findAll() throws JsonProcessingException {
        PersonDTO firstPerson = new PersonDTO();
        mockPerson();
        firstPerson = person;

        // Cria a primeira pessoa no sistema
        var createResponse1 = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(firstPerson)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().body().asString();

        firstPerson = objectMapper.readValue(createResponse1, PersonDTO.class);

        // Cria uma segunda pessoa com nome diferente
        PersonDTO secondPerson = new PersonDTO();
        secondPerson.setFirstName("Ana Paula");
        secondPerson.setLastName("Henriques");
        secondPerson.setAddress("Santa Maria - RS");
        secondPerson.setGender("Feminino");
        secondPerson.setEnabled(true);

        // Cria a segunda pessoa no sistema
        var createResponse2 = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(secondPerson)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().body().asString();

        secondPerson = objectMapper.readValue(createResponse2, PersonDTO.class);

        // Busca todas as pessoas com paginação
        var response = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .queryParam("direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().body().asString();

        // Desserialização usando o wrapper
        WrapperPersonDTO wrapper = objectMapper.readValue(response, WrapperPersonDTO.class);
        List<PersonDTO> people = wrapper.getEmbedded().getPeople();

        // Verifica se a lista contém pessoas
        assertNotNull(people);
        assertFalse(people.isEmpty());

        // Verifica se encontra as pessoas criadas
        boolean foundFirstPerson = false;
        boolean foundSecondPerson = false;

        for (PersonDTO p : people) {
            if (p.getId().equals(firstPerson.getId())) {
                foundFirstPerson = true;
                assertEquals("Alina", p.getFirstName());
                assertEquals("Phettis", p.getLastName());
                assertEquals("7th Floor", p.getAddress());
                assertEquals("Female", p.getGender());
                assertTrue(p.getEnabled());
            } else if (p.getId().equals(secondPerson.getId())) {
                foundSecondPerson = true;
                assertEquals("Ana Paula", p.getFirstName());
                assertEquals("Henriques", p.getLastName());
                assertEquals("Santa Maria - RS", p.getAddress());
                assertEquals("Feminino", p.getGender());
                assertTrue(p.getEnabled());
            }
        }

        assertTrue(foundFirstPerson, "Não encontrou a primeira pessoa criada");
        assertTrue(foundSecondPerson, "Não encontrou a segunda pessoa criada");
    }

    private void mockPerson() {
        person.setFirstName("Alina");
        person.setLastName("Phettis");
        person.setAddress("7th Floor");
        person.setGender("Female");
        person.setEnabled(true);
    }

}