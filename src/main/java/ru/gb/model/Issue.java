package ru.gb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name="issues")
@NoArgsConstructor
public class Issue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "bookId")
  private long bookId;
  @Column(name = "readerId")
  private long readerId;
  @Column(name = "issuedAtDate")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime issuedAt;
  @Column(name = "returnedAtDate")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime returnedAt;

  public Issue(Long bookId, Long readerId) {
    this.bookId = bookId;
    this.readerId = readerId;
    this.issuedAt = LocalDateTime.now().withNano(0);
  }
  public void closeIssue() {
    this.returnedAt = LocalDateTime.now().withNano(0);
  }

  public String formatDate(LocalDateTime timeToFormat) {
    if (timeToFormat == null) {
      return "";
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return timeToFormat.format(formatter);
  }
}
