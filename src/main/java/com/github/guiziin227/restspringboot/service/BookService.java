package com.github.guiziin227.restspringboot.service;

import com.github.guiziin227.restspringboot.controller.BookController;
import com.github.guiziin227.restspringboot.dto.BookDTO;
import com.github.guiziin227.restspringboot.model.Book;
import com.github.guiziin227.restspringboot.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.github.guiziin227.restspringboot.dto.mapper.ObjectMapper.parseListObjects;
import static com.github.guiziin227.restspringboot.dto.mapper.ObjectMapper.parseObject;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    @Autowired
    PagedResourcesAssembler<BookDTO> assembler;


    public BookDTO create(BookDTO book) {
        logger.info("Creating one book!");
        Book entity = parseObject(book, Book.class);
        BookDTO dto = parseObject(bookRepository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public BookDTO update(BookDTO book) {
        logger.info("Updating one book!");
        Book entity = bookRepository.findById(book.getId()).orElseThrow(
                () -> new RuntimeException("Book not found!")
        );
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setPrice(book.getPrice());
        BookDTO dto = parseObject(bookRepository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<BookDTO>> findAll(Pageable pageable) {
        logger.info("findAll books");

        var books = bookRepository.findAll(pageable);

        var booksWithLinks = books.map(book -> {
            BookDTO dto = parseObject(book, BookDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                BookController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), String.valueOf(pageable.getSort())))
                .withRel("findAll");
        return assembler.toModel(booksWithLinks, findAllLink);
    }

    @Transactional(readOnly = true)
    public BookDTO findById(Long id) {
        logger.info("findById book: " + id);
        Book entity = bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Book not found!")
        );
        BookDTO dto = parseObject(entity, BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting book with id: " + id);
        Book entity = bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Book not found!")
        );
        bookRepository.delete(entity);
    }

    private static void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findAll(0,10,"asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
    }
}
