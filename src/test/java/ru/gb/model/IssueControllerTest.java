package ru.gb.model;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.UnitSpringBootBase;
import ru.gb.repository.BookRepository;
import ru.gb.repository.IssueRepository;
import ru.gb.repository.ReaderRepository;
import java.time.LocalDateTime;

public class IssueControllerTest extends UnitSpringBootBase {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReaderRepository readerRepository;

    @Data
    static class JUnitIssue {
        private Long id;
        private Long bookId;
        private Long readerId;
        private LocalDateTime issuedAt;
        private LocalDateTime returnedAt;
    }

    @Data
    static class JUnitRequest {
        private Long readerId;
        private Long bookId;
    }

    @Test
    void getIssueByIdTest() {
        Issue testIssue = setUpDataBaseAndGetTestIssue();
        Issue expectedIssue = issueRepository.save(testIssue);
        JUnitIssue responseBody = webTestClient.get()
                .uri("/issue/" + expectedIssue.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitIssue.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expectedIssue.getId(), responseBody.getId());
        Assertions.assertEquals(expectedIssue.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(expectedIssue.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(expectedIssue.getIssuedAt(), responseBody.getIssuedAt());
        Assertions.assertNull(expectedIssue.getReturnedAt());
    }
    @Test
    void getIssueByIdNotFoundTest() {
        Long maxId = maxValueIssue();
        webTestClient.get()
                .uri("/issue/" + maxId)
                .exchange()
                .expectStatus().isNotFound();
    }
    @Test
    void issueBookCreatedTest() {
        Issue testIssue = setUpDataBaseAndGetTestIssue();
        JUnitRequest testIssueRequest = new JUnitRequest();
        testIssueRequest.setBookId(testIssue.getBookId());
        testIssueRequest.setReaderId(testIssue.getReaderId());
        JUnitIssue responseBody = webTestClient.post()
                .uri("/issue")
                .bodyValue(testIssueRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitIssue.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(testIssueRequest.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(testIssueRequest.getReaderId(), responseBody.getReaderId());
        Assertions.assertNotNull(responseBody.getIssuedAt());
        Assertions.assertNull(responseBody.getReturnedAt());
        Assertions.assertTrue(issueRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    void issueBookConflictTest() {
        Issue testIssue1 = setUpDataBaseAndGetTestIssue();
        Issue testIssue2 = new Issue(testIssue1.getBookId(), testIssue1.getReaderId());
        Issue testIssue3 = new Issue(testIssue1.getBookId(), testIssue1.getReaderId());
        issueRepository.save(testIssue1);
        issueRepository.save(testIssue2);
        issueRepository.save(testIssue3);
        JUnitRequest testIssueRequest = new JUnitRequest();
        testIssueRequest.setBookId(testIssue1.getBookId());
        testIssueRequest.setReaderId(testIssue1.getReaderId());
        webTestClient.post()
                .uri("/issue")
                .bodyValue(testIssueRequest)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }
    @Test
    void returnBookTest() {
        Issue testIssue = setUpDataBaseAndGetTestIssue();
        Issue expectedIssue = issueRepository.save(testIssue);
        JUnitIssue responseBody = webTestClient.put()
                .uri("/issue/" + expectedIssue.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitIssue.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expectedIssue.getId(), responseBody.getId());
        Assertions.assertEquals(expectedIssue.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(expectedIssue.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(expectedIssue.getIssuedAt(), responseBody.getIssuedAt());
        Assertions.assertNotNull(responseBody.getReturnedAt());
        Assertions.assertTrue(issueRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    void returnBookNotFoundTest() {
        Long maxId = maxValueIssue();
        webTestClient.put()
                .uri("/issue/" + maxId)
                .exchange()
                .expectStatus().isNotFound();
    }

    private Issue setUpDataBaseAndGetTestIssue() {
        Book testIssuedBook = new Book();
        testIssuedBook.setName("TestIssuedBook");
        Book expectedIssuedBook = bookRepository.save(testIssuedBook);
        Reader testIssuedReader = new Reader();
        testIssuedReader.setName("TestIssuedReader");
        Reader expectedIssuedReader = readerRepository.save(testIssuedReader);
        return new Issue(expectedIssuedBook.getId(), expectedIssuedReader.getId());
    }
}
