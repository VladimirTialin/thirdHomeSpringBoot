package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;
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
    public Book getById(@PathVariable long id) {
        return bookRepository.findById(id).get();
    }
    @DeleteMapping(value = "/{id}")
    public void removeBookById(@PathVariable long id) {
        bookRepository.deleteById(id);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Book saveBook(@RequestBody Book book) {
       return  bookRepository.saveAndFlush(book);
    }
}

