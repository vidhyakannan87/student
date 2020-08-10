package com.example.Tuition.controller;

import com.example.Tuition.api.request.StripeTokenRequest;
import com.example.Tuition.api.request.StudentLoginRequest;
import com.example.Tuition.api.request.StudentRequest;
import com.example.Tuition.api.response.AuthenticationResponse;
import com.example.Tuition.model.Student;
import com.okta.authn.sdk.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/students")
public interface StudentController {

  @PostMapping("/sign-up")
  ResponseEntity signUpStudent(@RequestBody StudentRequest studentRequest);

  @PostMapping("/login")
  ResponseEntity login(@RequestBody StudentLoginRequest studentLoginRequest) throws IOException, AuthenticationException;

  @GetMapping("/me")
  ResponseEntity getStudentFromBearerToken();

  @PostMapping(value = "/{id}/payment")
  @PreAuthorize("#id == authentication.name")
  ResponseEntity setPayment(@PathVariable String id, @RequestBody StripeTokenRequest stripeTokenRequest) throws StripeException, AuthenticationException, IOException;


}
