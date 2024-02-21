package ru.gb.api;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Book;
import ru.gb.repository.BookRepository;
import ru.gb.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable @Parameter(description = "'ID' книги") Long id) {
        Book queryBook = bookService.getBookById(id);
        if (queryBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(queryBook);
    }
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeBook(@PathVariable @Parameter(description = "'ID' книги") Long id) {
        Book queryBook = bookService.getBookById(id);
        if (queryBook == null) {
            return ResponseEntity.notFound().build();
        }
        bookService.removeBookById(id);
        return ResponseEntity.noContent().build();
    }
}


