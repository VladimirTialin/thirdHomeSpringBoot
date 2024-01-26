package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="issues")
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

}
