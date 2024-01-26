package ru.gb.springdemo.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.ValidateReaderException;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class IssuerService {
  @Autowired
  private  AllowedCountBooks countBook;
  @Autowired
  private  BookRepository bookRepository;
  @Autowired
  private  ReaderRepository readerRepository;
  @Autowired
  private  IssueRepository issueRepository;
  public Issue issue(Issue request) {
    AtomicLong count = new AtomicLong();
    if (bookRepository.findById(request.getBookId())==null) {
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (readerRepository.findById(request.getReaderId()) == null) {
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }

    issueRepository.findAll().forEach(x->{
      if(x.getReturnedAt()==null
        && x.getReaderId()==request.getReaderId())
        count.set(count.incrementAndGet());
    });
    if (count.get() == countBook.getCount())
      throw new ValidateReaderException("Читателю с идентификатором \""
              + request.getReaderId() + "\" " + "выдано книг: \""+count.get());
    issueRepository.saveAndFlush(request);
    return request;
  }
}
