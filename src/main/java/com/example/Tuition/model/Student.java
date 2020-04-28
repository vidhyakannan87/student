package com.example.Tuition.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

import static com.example.Tuition.model.BaseEntity.ALLOCATION_SIZE;
import static com.example.Tuition.model.BaseEntity.INITIAL_VALUE;

@Entity
@Data
public class Student {

  private static final String NAME = "student_id_sequence";
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

  private String firstName;

  private String lastName;

  @Column(unique = true)
  private String userName;

  @Column(unique = true)
  private String uuid;

  @JsonIgnore
  private String externalId;

  @Column(unique = true)
  private String email;

  private EducationBoard educationBoard;

}
