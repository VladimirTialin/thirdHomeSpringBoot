package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AllowedBooks {
@Value("${application.max-allowed-books}")
private int allowedBooks;

    public int getAllowedBooks() {
        return allowedBooks;
    }

    public void setAllowedBooks(int allowedBooks) {
        if(allowedBooks<1) allowedBooks=getAllowedBooks();
        this.allowedBooks = allowedBooks;
    }
}
