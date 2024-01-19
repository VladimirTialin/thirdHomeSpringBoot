package ru.gb.springdemo.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
@RequiredArgsConstructor
public class Issue {

  public static long sequence = 1L;
  private final long id;
  private final long bookId;
  private final long readerId;
  private final LocalDateTime issuedAt;
  @Setter
  private LocalDateTime returnedAt;
  public Issue(long bookId, long readerId) {
    this.id = sequence++;
    this.bookId = bookId;
    this.readerId = readerId;
    this.issuedAt  = LocalDateTime.now();
    this.returnedAt = null;
  }
}
