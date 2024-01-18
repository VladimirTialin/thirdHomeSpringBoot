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
import ru.gb.springdemo.service.AllowedBooks;
import ru.gb.springdemo.service.IssuerService;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
public class IssuerController {

  @Autowired
  private  IssuerService service;
  @Autowired
  private IssueRepository issueRepository;
  @Autowired
  private AllowedBooks allowedBooks;

//  @PutMapping
//  public void returnBook(long issueId) {
//    // найти в репозитории выдачу и проставить ей returned_at
//  }

  @GetMapping("/{id}")
  public Issue getById(@PathVariable long id) {
    return issueRepository.getIssueById(id);
  }
  @GetMapping("/allowed")
  public int setMaxAllowedBooks(@RequestParam int count) {
    allowedBooks.setAllowedBooks(count);
    return allowedBooks.getAllowedBooks();
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
