package ru.gb.springdemo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="issues")
@Schema(name="Запросы")
public class Issue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(name="идентификатор запроса")
  private long id;
  @Column(name = "bookId")
  @Schema(name="идентификатор книги")
  private long bookId;
  @Column(name = "readerId")
  @Schema(name="идентификатор читателя")
  private long readerId;
  @Column(name = "issuedAtDate")
  @Temporal(TemporalType.TIMESTAMP)
  @Schema(name="Время получения книги")
  private LocalDateTime issuedAt;
  @Column(name = "returnedAtDate")
  @Temporal(TemporalType.TIMESTAMP)
  @Schema(name="Время возвращения книги")
  private LocalDateTime returnedAt;

}
