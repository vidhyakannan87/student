package com.example.Student.api.request;

import com.example.Student.model.EducationBoard;
import com.example.Student.model.Interests;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class StudentRequest {

  private String firstName;

  private String lastName;

  @NotBlank
  private String userName;

  @NotBlank
  private String email;

  @NotBlank
  private String password;

  private EducationBoard educationBoard;

  private String phoneNumber;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate dateOfBirth;

  private List<Interests> interests;
}
