package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@Getter
public class ReaderRepository {

  private final List<Reader> readers;

  public ReaderRepository() {
    this.readers = new ArrayList<>();
  }

  @PostConstruct
  public void generateData() {
    readers.addAll(List.of(
            new Reader("Игорь"),
            new Reader("Иван"),
            new Reader("Василий"),
            new Reader("Сергей")
    ));
  }

  public Reader getReaderById(long id) {
    return readers.stream().filter(it -> Objects.equals(it.getId(), id))
      .findFirst()
      .orElse(null);
  }
  public boolean removeReaderById(long id){
    return readers.removeIf(book -> book.getId()==id);
  }
  public boolean add(Reader reader){
    return readers.add(reader);
  }
}
