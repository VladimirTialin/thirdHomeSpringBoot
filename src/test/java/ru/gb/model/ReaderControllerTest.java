package ru.gb.model;

import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.UnitSpringBootBase;
import ru.gb.repository.BookRepository;
import ru.gb.repository.IssueRepository;
import ru.gb.repository.ReaderRepository;
import java.util.List;

public class ReaderControllerTest extends UnitSpringBootBase {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ReaderRepository readerRepository;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    BookRepository bookRepository;

    Book testBook;
    Reader testReader;

    @Data
    static class JUnitReader {
        private Long id;
        private String name;
        private List<Issue> listIssues;
    }

    @BeforeEach
    void setUp() {
        Book testBook = new Book();
        testBook.setName("testBook");
        this.testBook = bookRepository.save(testBook);
        Reader testReader = new Reader();
        testReader.setName("testReader");
        this.testReader = readerRepository.save(testReader);
    }

    @AfterEach
    void clear() {
        issueRepository.deleteAll();
    }

    @Test
    void getReaderByIdTest() {
        JUnitReader responseBody = webTestClient.get()
                .uri("/reader/" + testReader.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitReader.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(testReader.getId(), responseBody.getId());
        Assertions.assertEquals(testReader.getName(), responseBody.getName());
    }

    @Test
    void getReaderByIdNotFoundTest() {
        Long max = maxValueReader();
        webTestClient.get()
                .uri("/reader/" + max)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getReaderIssuesNoContentTest() {
        webTestClient.get()
                .uri("/reader/" + testReader.getId() + "/issue")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void getReaderIssuesNotFoundTest() {
        Long max = maxValueReader();

        webTestClient.get()
                .uri("/reader/" + max + "/issue")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void addReaderTest() {
        JUnitReader responseBody = webTestClient.post()
                .uri("/reader")
                .bodyValue(testReader)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitReader.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(responseBody.getName(), testReader.getName());
        Assertions.assertTrue(readerRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    void removeReaderTest() {
        webTestClient.delete()
                .uri("/reader/" + testReader.getId())
                .exchange()
                .expectStatus().isNoContent();
    }
    @Test
    void removeReaderByIdNotFoundTest() {
        Long max= maxValueReader();
        webTestClient.delete()
                .uri("/reader/" + max)
                .exchange()
                .expectStatus().isNotFound();
    }
}
