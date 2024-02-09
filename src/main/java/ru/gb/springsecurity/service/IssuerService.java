package ru.gb.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gb.springsecurity.model.ReaderProperties;
import ru.gb.springsecurity.model.Issue;
import ru.gb.springsecurity.model.ValidateReaderException;
import ru.gb.springsecurity.repository.BookRepository;
import ru.gb.springsecurity.repository.IssueRepository;
import ru.gb.springsecurity.repository.ReaderRepository;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class IssuerService {
  @Autowired
  private ReaderProperties readerProperties;
  @Autowired
  private  BookRepository bookRepository;
  @Autowired
  private  ReaderRepository readerRepository;
  @Autowired
  private IssueRepository issueRepository;
  public Issue newIssue(Issue request) {
    AtomicLong count = new AtomicLong();
    if (bookRepository.findById(request.getBookId())==null){
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (readerRepository.findById(request.getReaderId())==null) {
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }

    issueRepository.findAll().forEach(x->{
      if(x.getReturnedAt()==null
        && x.getReaderId()==request.getReaderId())
        count.set(count.incrementAndGet());
    });
    if(readerProperties.getCount()<1)
      readerProperties.setCount(1);
    if (count.get() == readerProperties.getCount())
      throw new ValidateReaderException("Читателю с идентификатором \""
              + request.getReaderId() + "\" " + "выдано книг: \""+count.get());
    issueRepository.saveAndFlush(request);
    return request;
  }
}
