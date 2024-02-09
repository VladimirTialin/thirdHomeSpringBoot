package ru.gb.springsecurity.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.gb.springsecurity.repository.BookRepository;
import ru.gb.springsecurity.repository.IssueRepository;
import ru.gb.springsecurity.repository.ReaderRepository;

@Controller
@RequestMapping("ui")
public class UIController {
    @Autowired
    private  BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private  IssueRepository issueRepository;
    @GetMapping("/books")
    public String allBooks( Model model){
        model.addAttribute("books",bookRepository.findAll());
        return "books";
    }
    @GetMapping("/reader")
    public String allReaders(Model model) {
        model.addAttribute("readers", readerRepository.findAll());
        return "readers";
    }
    @GetMapping("/issues")
    public String allIssues(ModelMap model){
        model.addAttribute("issues",issueRepository.findAll());
        model.addAttribute("readers",readerRepository.findAll());
        model.addAttribute("books",bookRepository.findAll());
        return "issues";
    }
}

