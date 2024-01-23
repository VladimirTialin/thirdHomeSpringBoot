package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return readerRepository.getReaders();
    }


    @GetMapping("/{id}")
    public Reader getById(@PathVariable long id) {
        return readerRepository.getReaderById(id);
    }

    @DeleteMapping(value = "/{id}")
    public boolean removeReaderById(@PathVariable long id) {
        return readerRepository.removeReaderById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addReader(@RequestBody Reader reader) {
        return readerRepository.add(reader);
    }
    @GetMapping("/{id}/issue")
    public List<Issue> getReaderIssue(@PathVariable long id) {
         return issueRepository.getIssues()
                 .stream()
                 .filter(issue -> Objects.equals(issue.getReaderId(),id)).toList();
    }
}

