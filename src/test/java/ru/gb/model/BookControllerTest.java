package ru.gb.model;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.UnitSpringBootBase;
import ru.gb.repository.BookRepository;

public class BookControllerTest extends UnitSpringBootBase {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    BookRepository bookRepository;

    @Data
    static class JUnitBook {
        private Long id;
        private String name;
    }

    @Test
    void getBookByIdTest() {
        Book testBook = new Book();
        testBook.setName("TestBook");
        Book expected = bookRepository.save(testBook);
        JUnitBook responseBody = webTestClient.get()
                .uri("/book/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBook.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
    }

    @Test
    void getBookByIdNotFoundTest() {
        Long max = maxValueBook();
        webTestClient.get()
                .uri("/book/" + max)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void saveBookTest() {
        JUnitBook testBook = new JUnitBook();
        testBook.setName("saveNewBook");
        JUnitBook responseBody = webTestClient.post()
                .uri("/book")
                .bodyValue(testBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitBook.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(responseBody.getName(), testBook.getName());
        Assertions.assertTrue(bookRepository.findById(responseBody.getId()).isPresent());
    }
    @Test
    void removeBookByIdTest() {
        Book testBook = new Book();
        testBook.setName("testBook");
        Book bookForRemove = bookRepository.save(testBook);
        webTestClient.delete()
                .uri("/book/" + bookForRemove.getId())
                .exchange()
                .expectStatus().isNoContent();
    }
    @Test
    void removeBookByIdNotFoundTest() {
        Long max = maxValueBook();
        webTestClient.delete()
                .uri("/book/" + max)
                .exchange()
                .expectStatus().isNotFound();
    }

}
