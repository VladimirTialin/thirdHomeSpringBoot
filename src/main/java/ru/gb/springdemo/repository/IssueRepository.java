package ru.gb.springdemo.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Repository
public class IssueRepository {
  private final List<Issue> issues;

  public IssueRepository() {
    this.issues = new ArrayList<>();
  }
  public Issue getIssueById(long id) {
    return issues.stream().filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElse(null);
  }
  public void save(Issue issue) {
    // insert into ....
    issues.add(issue);
  }

}
