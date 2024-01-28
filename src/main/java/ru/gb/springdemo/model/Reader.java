package ru.gb.springdemo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Check;

@Data
@Entity
@Table(name="readers")
@Schema(name="Читатель")
public class Reader {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(name="идентификатор")
  private long id;
    @Column(name="readerName")
    @Schema(name="Имя читателя")
  private String name;
}
