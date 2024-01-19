package ru.gb.springdemo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.api.request.IssueRequest;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.model.ValidateReaderException;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
@AllArgsConstructor
public class IssuerService {


  private final AllowedBooks countBook;
  private final BookRepository bookRepository;
  private final ReaderRepository readerRepository;
  private final IssueRepository issueRepository;
  public Issue issue(IssueRequest request) {
    AtomicLong count = new AtomicLong();
    if (bookRepository.getBookById(request.getBookId()) == null) {
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (readerRepository.getReaderById(request.getReaderId()) == null) {
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }

    issueRepository.getIssues().forEach(x->{
      if(x.getReturnedAt()==null
        && x.getReaderId()==request.getReaderId())
        count.set(count.incrementAndGet());
    });
    if (count.get() ==countBook.getAllowedBooks())
      throw new ValidateReaderException("Читателю с идентификатором \""
              + request.getReaderId() + "\" " + "выдано книг: \""+count);


    Issue issue = new Issue(request.getBookId(), request.getReaderId());
    issueRepository.save(issue);
    return issue;
  }
}
