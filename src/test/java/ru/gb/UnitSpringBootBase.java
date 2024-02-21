package ru.gb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public abstract class UnitSpringBootBase {
    @Autowired
    JdbcTemplate jdbcTemplate;
    protected Long maxValueBook(){
        Long maxId = jdbcTemplate.queryForObject("select max(id) from books", Long.class);
        if (maxId == null)  return 1L;
        else return maxId + 1L;
    }
    protected Long maxValueReader(){
        Long maxId = jdbcTemplate.queryForObject("select max(id) from reader", Long.class);
        if (maxId == null)  return 1L;
        else return maxId + 1L;
    }
    protected Long maxValueIssue(){
        Long maxId = jdbcTemplate.queryForObject("select max(id) from issues", Long.class);
        if (maxId == null)  return 1L;
        else return maxId + 1L;
    }
}
