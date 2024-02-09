package ru.gb.springsecurity.api;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gb.springsecurity.model.Issue;
import ru.gb.springsecurity.model.Reader;
import ru.gb.springsecurity.repository.IssueRepository;
import ru.gb.springsecurity.repository.ReaderRepository;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    private  ReaderRepository readerRepository;
    @Autowired
    private IssueRepository issueRepository;

    @GetMapping
    public List<Reader>  getReadersAll(){
        return readerRepository.findAll();
    }


    @GetMapping("/{id}")
    public Reader getReaderById(@PathVariable @Parameter(description = "идентификатор") long id) {
        return readerRepository.findById(id).get();
    }

    @DeleteMapping(value = "/{id}")
    public void removeReaderById(@PathVariable long id) {
        readerRepository.deleteById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Reader saveReader(@RequestBody Reader reader) {
        return readerRepository.saveAndFlush(reader);
    }
    @GetMapping("/{id}/issue")
    public List<Issue> getReaderIssue(@PathVariable @Parameter(description = "идентификатор") long id) {
         return issueRepository.findAll()
                 .stream()
                 .filter(issue -> Objects.equals(issue.getReaderId(),id)).toList();
    }
}

