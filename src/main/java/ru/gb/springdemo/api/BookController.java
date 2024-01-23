package ru.gb.springdemo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookRepository bookRepository;
    @GetMapping
    public List<Book>  getBooksAll(){
        return bookRepository.getBooks();
    }


    @GetMapping("/{id}")
    public Book getById(@PathVariable long id) {
        return bookRepository.getBookById(id);
    }
    @DeleteMapping(value = "/{id}")
    public boolean removeBookById(@PathVariable long id) {
        return bookRepository.removeBookById(id);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addBook(@RequestBody Book book) {
        return bookRepository.add(book);
    }
}

