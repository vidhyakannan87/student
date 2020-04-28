package com.example.Tuition.api.request;

import com.example.Tuition.model.EducationBoard;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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
}
