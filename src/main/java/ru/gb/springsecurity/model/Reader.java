package ru.gb.springsecurity.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Check;

@Data
@Entity
@Table(name="readers")
public class Reader {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
    @Column(name="readerName")
  private String name;
}
