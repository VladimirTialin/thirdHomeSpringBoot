package ru.gb.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="reader")
@NoArgsConstructor
public class Reader {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
    @Column(name="readerName")
  private String name;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "readerId")
  private List<Issue> listIssues = new ArrayList<>();

}
