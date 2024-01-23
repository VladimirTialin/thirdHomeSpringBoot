package ru.gb.springdemo.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("ui")
public class UIController {
    private List<Issue> issueList= new ArrayList<>();
    @Autowired
    private  BookRepository bookRepository;
    @Autowired
    private  ReaderRepository readerRepository;
    @Autowired
    private  IssueRepository issueRepository;
    @GetMapping("/books")
    public String allBooks( Model model){
        model.addAttribute("books",bookRepository.getBooks());
        return "books";
    }
    @GetMapping("/reader")
    public String allReaders(Model model) {
        model.addAttribute("readers", readerRepository.getReaders());
        return "readers";
    }
    @GetMapping("/issues")
    public String allIssues(ModelMap model){
        issueList= issueRepository.getIssues();
        issueList.add(new Issue(1,1));
        issueList.add(new Issue(2,3));
        issueList.add(new Issue(3,2));
        issueList.add(new Issue(4,4));
        issueList.forEach(x ->{
            if(x.getId()!=2) x.setReturnedAt(LocalDateTime
                    .of(2024, Month.JANUARY,25,12,15, 0));
        });
        model.addAttribute("issues",issueList);
        model.addAttribute("readers",readerRepository.getReaders());
        model.addAttribute("books",bookRepository.getBooks());
        return "issues";
    }
}

