package ru.gb.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.springsecurity.model.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long>{
}
