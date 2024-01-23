package ru.gb.springdemo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.api.request.IssueRequest;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.ValidateReaderException;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.service.AllowedCountBooks;
import ru.gb.springdemo.service.IssuerService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issues")
public class IssuerController {

  @Autowired
  private  IssuerService service;
  @Autowired
  private IssueRepository issueRepository;
  @Autowired
  private AllowedCountBooks allowedBooks;
  @PutMapping("/{issueId}")
  public void returnBook(@PathVariable long issueId) {
    issueRepository.getIssues().forEach(issue -> {
      if(issue.getId()==issueId)
        issue.setReturnedAt(LocalDateTime.now());
    });
  }

  @GetMapping("/{id}")
  public Issue getById(@PathVariable long id) {
    return issueRepository.getIssueById(id);
  }
  @GetMapping("/allowed")
  public int setMaxAllowedBooks(@RequestParam int count) {
    allowedBooks.setCount(count);
    return allowedBooks.getCount();
  }
  @PostMapping
  public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
    log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());

    final Issue issue;
    try {
      issue = service.issue(request);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }catch (ValidateReaderException e){
      return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    return ResponseEntity.status(HttpStatus.OK).body(issue);
  }

}
