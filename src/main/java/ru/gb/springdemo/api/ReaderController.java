package ru.gb.springdemo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Читатели")
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    private  ReaderRepository readerRepository;
    @Autowired
    private IssueRepository issueRepository;

    @GetMapping
    @Operation(summary = "Список всех читателей", description = "Получаем список всех читателей")
    public List<Reader>  getReadersAll(){
        return readerRepository.findAll();
    }


    @GetMapping("/{id}")
    @Operation(summary = "Идентификатор читателя", description = "Получить имя читателя по идентификатору")
    public Reader getReaderById(@PathVariable @Parameter(description = "идентификатор") long id) {
        return readerRepository.findById(id).get();
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Удаление записи читателя", description = "Удалить запись читателя по идентификатору")
    public void removeReaderById(@PathVariable long id) {
        readerRepository.deleteById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавить книгу", description = "Добавить новую книгу")
    public Reader saveReader(@RequestBody Reader reader) {
        return readerRepository.saveAndFlush(reader);
    }
    @GetMapping("/{id}/issue")
    @Operation(summary = "Список книг по идентефикатору", description = "Получаем список всех книг, которые получил на руки определенный читатель по его идентификатору")
    public List<Issue> getReaderIssue(@PathVariable @Parameter(description = "идентификатор") long id) {
         return issueRepository.findAll()
                 .stream()
                 .filter(issue -> Objects.equals(issue.getReaderId(),id)).toList();
    }
}

