package com.example.Student.api.request;

import com.example.Student.model.EducationBoard;
import com.example.Student.model.Interests;
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
