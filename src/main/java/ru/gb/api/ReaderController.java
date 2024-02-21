package ru.gb.api;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Issue;
import ru.gb.model.Reader;
import ru.gb.repository.IssueRepository;
import ru.gb.repository.ReaderRepository;
import ru.gb.service.ReaderService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReader(@PathVariable Long id) {
        Reader queryReader = readerService.getReaderById(id);
        if (queryReader == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(queryReader);
    }

    @GetMapping("/{id}/issue")
    public ResponseEntity<List<Issue>> getReaderIssues(@PathVariable  Long id) {
        List<Issue> queryIssue = readerService.getIssueByReaderId(id);
        if (queryIssue == null) {
            return ResponseEntity.notFound().build();
        }
        if (queryIssue.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(queryIssue);
    }

    @PostMapping
    public ResponseEntity<Reader> addReader(@RequestBody Reader reader) {
        readerService.addReader(reader);
        return ResponseEntity.status(HttpStatus.CREATED).body(reader);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeReader(@PathVariable Long id) {
        Reader queryReader = readerService.getReaderById(id);
        if (queryReader == null) {
            return ResponseEntity.notFound().build();
        }
        readerService.removeReaderById(id);
        return ResponseEntity.noContent().build();
    }
}

