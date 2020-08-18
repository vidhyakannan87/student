package com.example.Tuition.api.request;

import com.example.Tuition.model.EducationBoard;
import com.example.Tuition.model.Interests;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class StudentUpdateRequest  extends UpdatePasswordRequest{

  private String firstName;

  private String lastName;

  private String userName;

  private String email;

  private EducationBoard educationBoard;

  private String phoneNumber;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate dateOfBirth;

  private List<Interests> interests;

}
