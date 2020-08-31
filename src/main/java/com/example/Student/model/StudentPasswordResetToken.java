package com.example.Student.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class StudentPasswordResetToken extends  BaseEntity {

  private static final String NAME = "student_password_reset_token_id_sequence";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NAME)
  @SequenceGenerator(
          name = NAME,
          sequenceName = NAME,
          initialValue = INITIAL_VALUE,
          allocationSize = ALLOCATION_SIZE
  )
  @JsonIgnore
  private long id;

  //in minutes
  private static final int EXPIRATION = 20;

  public StudentPasswordResetToken() {

    this.expiryDate = calculateExpiryDate();
  }

  @NotNull
  private String token;

  @OneToOne
  private Student student;

  @NotNull
  private LocalDateTime expiryDate;

  private LocalDateTime calculateExpiryDate() {

    LocalDateTime now = LocalDateTime.now();

    return now.plusMinutes(EXPIRATION);
  }
}
