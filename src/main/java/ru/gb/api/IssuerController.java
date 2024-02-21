package ru.gb.api;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Issue;
import ru.gb.model.ValidateReaderException;
import ru.gb.repository.IssueRepository;
import ru.gb.model.ReaderProperties;
import ru.gb.service.IssuerService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssuerController {

  private final  IssuerService issuerService;

  @PutMapping("/{issueId}")
  public ResponseEntity<Issue> returnBook(@PathVariable Long issueId) {
    Issue closedIssue = issuerService.closeActiveIssue(issueId);
    if (closedIssue == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(closedIssue);
  }
  @GetMapping("/{id}")
  public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
    Issue queryIssue = issuerService.getIssueById(id);
    if (queryIssue == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(queryIssue);
  }
  @PostMapping
  public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
    log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());
    final Issue issue;
    try {
      issue = issuerService.issue(request);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(issue);
  }
}
