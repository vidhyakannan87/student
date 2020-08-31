package com.example.Student.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Interests {

  private static final String NAME = "interest_id_sequence";
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NAME)
  @SequenceGenerator(
          name = NAME,
          sequenceName = NAME,
          initialValue = BaseEntity.INITIAL_VALUE,
          allocationSize = BaseEntity.ALLOCATION_SIZE
  )
  @JsonIgnore
  @Id
  private long id;

  @ManyToOne
  @JsonBackReference
  private Student student;

  private String interest;


}
