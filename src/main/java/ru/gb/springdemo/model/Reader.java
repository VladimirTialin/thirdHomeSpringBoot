package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="readers")
public class Reader {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
    @Column(name="readerName")
  private String name;
}
