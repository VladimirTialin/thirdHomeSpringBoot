package ru.gb.springsecurity.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.springsecurity.model.Book;

@Repository
public interface BookRepository extends JpaRepository <Book,Long> {
}
