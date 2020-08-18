package com.example.Tuition.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.Tuition.model.BaseEntity.ALLOCATION_SIZE;
import static com.example.Tuition.model.BaseEntity.INITIAL_VALUE;

@Entity
@Getter
@Setter
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

  private String phoneNumber;

  @JsonIgnore
  private String stripeCustomerId;

  private LocalDate dateOfBirth;

  @OneToMany(mappedBy = "student", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Interests> interests = new ArrayList<>();

  private String profilePicUrl;

  public void setInterests(List<Interests> interests) {
    this.interests.clear();
    this.interests.addAll(interests);
  }

}
