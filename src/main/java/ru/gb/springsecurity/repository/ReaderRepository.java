package ru.gb.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.springsecurity.model.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader,Long> {

}
