package ru.gb.springdemo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.api.request.IssueRequest;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.ValidateReaderException;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class IssuerService {


  private final AllowedBooks countBook;
  private final BookRepository bookRepository;
  private final ReaderRepository readerRepository;
  private final IssueRepository issueRepository;
  public Issue issue(IssueRequest request) {
    if (bookRepository.getBookById(request.getBookId()) == null) {
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (readerRepository.getReaderById(request.getReaderId()) == null) {
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }
    long count= issueRepository.getIssues().stream()
            .filter(issue ->
                    issue.getReaderId()==request.getReaderId()).count();
      if (count==countBook.getAllowedBooks())
        throw new ValidateReaderException("Читателю с идентификатором \""
                + request.getReaderId() + "\" " + "выдано книг: \""+count);
    // можно проверить, что у читателя нет книг на руках (или его лимит не превышает в Х книг)

    Issue issue = new Issue(request.getBookId(), request.getReaderId());
    issueRepository.save(issue);
    return issue;
  }
}
