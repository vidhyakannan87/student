package com.example.Tuition.api.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StudentLoginRequest {

  @NotBlank
  String username;

  @NotBlank
  String password;
}
