package com.github.guiziin227.restspringboot.service;

import com.github.guiziin227.restspringboot.dto.BookDTO;
import com.github.guiziin227.restspringboot.dto.PersonDTO;
import com.github.guiziin227.restspringboot.model.Book;
import com.github.guiziin227.restspringboot.model.Person;
import com.github.guiziin227.restspringboot.repository.BookRepository;
import com.github.guiziin227.restspringboot.unitetests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook mockBook;

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        mockBook = new MockBook();
        Mockito.reset(bookRepository);
    }

    @Test
    void create() {
        Book book = mockBook.mockEntity(1);
        book.setId(1L);

        BookDTO dto = mockBook.mockDTO(1);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        var result = bookService.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Some Author1", result.getAuthor());
        assertEquals("2023-10-01 00:00:00.000000", result.getLaunchDate());
        assertEquals("Some Title1", result.getTitle());
        assertEquals(25D, result.getPrice());
    }

    @Test
    void update() {
        Book book = mockBook.mockEntity(1);
        book.setId(1L);

        BookDTO dto = mockBook.mockDTO(1);
        Mockito.when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(java.util.Optional.of(book));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        var result = bookService.update(dto);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Some Author1", result.getAuthor());
        assertEquals("2023-10-01 00:00:00.000000", result.getLaunchDate());
        assertEquals("Some Title1", result.getTitle());
        assertEquals(25D, result.getPrice());
    }

//    @Test
//    @Disabled("Disabled until pagination is implemented")
//    void findAll() {
//        List<Person> list = input.mockEntityList();
//
//        assertNotNull(people);
//        assertEquals(14, people.size());
//        Mockito.when(bookRepository.findAll()).thenReturn(mockBook.mockEntityList());
//        var result = bookService.findAll();
//        assertNotNull(result);
//        assertEquals(14, result.size());
//
//        for (int i = 0; i < 14; i++) {
//            BookDTO bookDTO = result.get(i);
//            assertNotNull(bookDTO.getId());
//            assertNotNull(bookDTO.getLinks());
//
//            int finalI = i;
//            assertNotNull(bookDTO.getLinks().stream()
//                    .anyMatch(link -> link.getRel().value().equals("self")
//                            && link.getHref().endsWith("/api/book/v1/" + finalI)
//                            && link.getType().equals("GET")
//                    ));
//
//            assertNotNull(bookDTO.getLinks().stream()
//                    .anyMatch(link -> link.getRel().value().equals("findAll")
//                            && link.getHref().endsWith("/api/book/v1")
//                            && link.getType().equals("GET")
//                    )
//            );
//
//            assertNotNull(bookDTO.getLinks().stream()
//                    .anyMatch(link -> link.getRel().value().equals("create")
//                            && link.getHref().endsWith("/api/book/v1")
//                            && link.getType().equals("POST")
//                    )
//            );
//
//            assertNotNull(bookDTO.getLinks().stream()
//                    .anyMatch(link -> link.getRel().value().equals("update")
//                            && link.getHref().endsWith("/api/book/v1")
//                            && link.getType().equals("PUT")
//                    )
//            );
//
//            int finalI1 = i;
//            assertNotNull(bookDTO.getLinks().stream()
//                    .anyMatch(link -> link.getRel().value().equals("delete")
//                            && link.getHref().endsWith("/api/book/v1/" + finalI1)
//                            && link.getType().equals("DELETE")
//                    )
//            );
//
//            assertEquals("Some Author" + i, bookDTO.getAuthor());
//            assertEquals("2023-10-01 00:00:00.000000", bookDTO.getLaunchDate());
//            assertEquals("Some Title" + i, bookDTO.getTitle());
//            assertEquals(25D, bookDTO.getPrice());
//        }
//
//    }

    @Test
    void findById() {
        Book book = mockBook.mockEntity(1);
        book.setId(1L);

        BookDTO dto = mockBook.mockDTO(1);
        Mockito.when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(java.util.Optional.of(book));
        var result = bookService.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Some Author1", result.getAuthor());
        assertEquals("2023-10-01 00:00:00.000000", result.getLaunchDate());
        assertEquals("Some Title1", result.getTitle());
        assertEquals(25D, result.getPrice());
    }

    @Test
    void delete() {
        Book book = mockBook.mockEntity(1);
        book.setId(1L);

        Mockito.when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(java.util.Optional.of(book));
        bookService.delete(1L);
        Mockito.verify(bookRepository, Mockito.times(1)).findById(Mockito.any
(Long.class));
        Mockito.verify(bookRepository, Mockito.times(1)).delete(Mockito.any(Book.class));
        Mockito.verifyNoMoreInteractions(bookRepository);
    }
}