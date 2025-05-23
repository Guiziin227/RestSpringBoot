package com.github.guiziin227.restspringboot.repository;

import com.github.guiziin227.restspringboot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
