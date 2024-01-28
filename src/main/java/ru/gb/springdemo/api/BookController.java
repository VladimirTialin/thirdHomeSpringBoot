package ru.gb.springdemo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;
import java.util.List;

@RestController
@Tag(name = "Книги")
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @GetMapping
    @Operation(summary = "Список всех книг", description = "Получаем список всех книг")
    public List<Book>  getBooksAll(){
        return bookRepository.findAll();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Идентификатор книги", description = "Получить название книги по идентификатору")
    public Book getById(@PathVariable @Parameter(description = "идентификатор") long id) {
        return bookRepository.findById(id).get();
    }
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Удаление книги", description = "Удалить книгу по идентификатору")
    public void removeBookById(@PathVariable @Parameter(description = "идентификатор") long id) {
        bookRepository.deleteById(id);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавить книгу", description = "Добавить новую книгу")
    public Book saveBook(@RequestBody Book book) {
       return  bookRepository.saveAndFlush(book);
    }
}

