package com.github.guiziin227.restspringboot.repository;

import com.github.guiziin227.restspringboot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying // Garantir ACID (Atomicidade, Consistência, Isolamento e Durabilidade)
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
    void disablePerson(@Param("id")Long id);
}
