package com.example.Tuition.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.example.Tuition.model.BaseEntity.ALLOCATION_SIZE;
import static com.example.Tuition.model.BaseEntity.INITIAL_VALUE;

@Entity
@Getter
@Setter
public class Interests {

  private static final String NAME = "interest_id_sequence";
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NAME)
  @SequenceGenerator(
          name = NAME,
          sequenceName = NAME,
          initialValue = INITIAL_VALUE,
          allocationSize = ALLOCATION_SIZE
  )
  @JsonIgnore
  @Id
  private long id;

  @ManyToOne
  @JsonBackReference
  private Student student;

  private String interest;


}
