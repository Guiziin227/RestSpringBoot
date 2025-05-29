package com.github.guiziin227.restspringboot.repository;

import com.github.guiziin227.restspringboot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
