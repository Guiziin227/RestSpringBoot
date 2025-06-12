package com.github.guiziin227.restspringboot.repository;

import com.github.guiziin227.restspringboot.model.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying(flushAutomatically = true) // Garantir ACID (Atomicidade, Consistência, Isolamento e Durabilidade)
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
    void disablePerson(@Param("id") Long id);

    @Query("SELECT p FROM Person p WHERE p.firstName like LOWER(CONCAT('%',:firstName,'%')) ")
    Page<Person> findPeopleByName(@Param("firstName") String firstName, Pageable pageable);
}
