package com.example.Tuition.controller;

import com.example.Tuition.api.request.StudentLoginRequest;
import com.example.Tuition.api.request.StudentRequest;
import com.example.Tuition.api.response.AuthenticationResponse;
import com.okta.authn.sdk.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequestMapping("/students")
public interface StudentController {

  @PostMapping
  void signUpStudent(@RequestBody StudentRequest studentRequest);

  @PostMapping("/login")
  AuthenticationResponse login(@RequestBody StudentLoginRequest studentLoginRequest) throws IOException, AuthenticationException;
}
