package ru.gb.springsecurity.api;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springsecurity.model.Issue;
import ru.gb.springsecurity.model.ValidateReaderException;
import ru.gb.springsecurity.repository.IssueRepository;
import ru.gb.springsecurity.model.ReaderProperties;
import ru.gb.springsecurity.service.IssuerService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
public class IssuerController {
  @Autowired
  private IssuerService issuerService;

  @Autowired
  private IssueRepository issueRepository;
  @Autowired
  private ReaderProperties readerProperties;
  @PutMapping("/{issueId}")
  public void returnBook(@PathVariable @Parameter(description = "идентификатор") long issueId) {
    issueRepository.findAll().forEach(issue -> {
      if(issue.getId()==issueId) {
        issue.setReturnedAt(LocalDateTime.now());
        issueRepository.saveAndFlush(issue);
      };
    });
  }

  @GetMapping("/{id}")
  public Issue getById(@PathVariable @Parameter(description = "идентификатор") long id) {
    return issueRepository.findById(id).get();
  }
  @GetMapping("/allowed")
  @Hidden
  public long setMaxAllowedBooks(@RequestParam Integer count) {
    readerProperties.setCount(count);
    return readerProperties.getCount();
  }
  @PostMapping
  public ResponseEntity<Issue> issueBook(@RequestBody Issue request) {
    log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());
    try {
      request.setIssuedAt(LocalDateTime.now());
      request.setReturnedAt(null);
      Issue issue = issuerService.newIssue(request);
      issueRepository.saveAndFlush(issue);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }catch (ValidateReaderException e){
      return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    return ResponseEntity.status(HttpStatus.OK).body(request);
  }

}
