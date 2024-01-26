package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;
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
    public Reader getReaderById(@PathVariable long id) {
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
    public List<Issue> getReaderIssue(@PathVariable long id) {
         return issueRepository.findAll()
                 .stream()
                 .filter(issue -> Objects.equals(issue.getReaderId(),id)).toList();
    }
}

