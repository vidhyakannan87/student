package com.example.Tuition.controller.impl;

import com.example.Tuition.api.request.StudentLoginRequest;
import com.example.Tuition.api.request.StudentRequest;
import com.example.Tuition.api.response.AuthenticationResponse;
import com.example.Tuition.controller.StudentController;
import com.example.Tuition.service.OktaAuthenticationService;
import com.example.Tuition.service.StudentService;
import com.okta.authn.sdk.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class StudentControllerImpl implements StudentController {

  private final StudentService studentService;
  private final OktaAuthenticationService oktaAuthenticationService;

  public StudentControllerImpl(StudentService studentService, OktaAuthenticationService oktaAuthenticationService) {
    this.studentService = studentService;
    this.oktaAuthenticationService = oktaAuthenticationService;
  }

  @Override
  public void signUpStudent(@RequestBody StudentRequest studentRequest) {
    studentService.signUpStudent(studentRequest);
  }

  @Override
  public AuthenticationResponse login(StudentLoginRequest studentLoginRequest) throws IOException, AuthenticationException {
    return oktaAuthenticationService.authenticateUser(studentLoginRequest);
  }
}
