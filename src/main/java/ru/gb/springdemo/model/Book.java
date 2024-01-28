package ru.gb.springdemo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
@Schema(name = "Книга")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(name="идентификатор")
  private long id;
  @Column(name="bookName")
  @Schema(name="название")
  private String name;
}
