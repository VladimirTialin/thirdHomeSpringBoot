package ru.gb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.gb.api.IssueRequest;
import ru.gb.model.Book;
import ru.gb.model.Issue;
import ru.gb.model.ReaderProperties;
import ru.gb.repository.BookRepository;
import ru.gb.repository.IssueRepository;
import ru.gb.repository.ReaderRepository;
import ru.gb.aspect.Timer;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@EnableConfigurationProperties(ReaderProperties.class)
public class IssuerService {
  @Autowired
  private ReaderProperties readerProperties;
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private ReaderRepository readerRepository;
  @Autowired
  private IssueRepository issueRepository;
  @Timer
  public Issue issue(IssueRequest request) {
    if (bookRepository.findById(request.getBookId()).isEmpty()) {
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (readerRepository.findById(request.getReaderId()).isEmpty()) {
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }
    if(issueRepository.findActiveIssuesByReaderId(request.getReaderId()).size() >= readerProperties.getCount()) {
      throw new SecurityException("Читателя с идентификатором \"" + request.getReaderId() + "\" больше не может брать книги");
    } else {
      Issue issue = new Issue(request.getBookId(), request.getReaderId());
      issueRepository.save(issue);
      return issue;
    }
  }
  public List<Issue> getAllIssues() {
    return issueRepository.findAll();
  }
  public Issue getIssueById(Long id) {
    return issueRepository.findById(id).orElse(null);
  }

  public List<Book> getIssuedBookByReaderId(Long id) {
    return bookRepository.findIssuedBookByReaderId(id);
  }
  public Issue closeActiveIssue(Long id) {
    Issue currentIssue = issueRepository.findById(id).orElse(null);
    if (currentIssue == null) {
      return null;
    }
    if (currentIssue.getReturnedAt() == null) {
      currentIssue.closeIssue();
      issueRepository.save(currentIssue);
    }
    return currentIssue;
  }
}
