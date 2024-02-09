package ru.gb.springsecurity.api;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gb.springsecurity.model.Book;
import ru.gb.springsecurity.repository.BookRepository;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @GetMapping
    public List<Book>  getBooksAll(){
        return bookRepository.findAll();
    }
    @GetMapping("/{id}")
    public Book getById(@PathVariable @Parameter(description = "идентификатор") long id) {
        return bookRepository.findById(id).get();
    }
    @DeleteMapping(value = "/{id}")
    public void removeBookById(@PathVariable @Parameter(description = "идентификатор") long id) {
        bookRepository.deleteById(id);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Book saveBook(@RequestBody Book book) {
       return  bookRepository.saveAndFlush(book);
    }
}

