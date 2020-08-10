package com.example.Tuition.controller.impl;

import com.example.Tuition.api.request.StripeTokenRequest;
import com.example.Tuition.api.request.StudentLoginRequest;
import com.example.Tuition.api.request.StudentRequest;
import com.example.Tuition.api.response.AuthenticationResponse;
import com.example.Tuition.controller.StudentController;
import com.example.Tuition.model.Student;
import com.example.Tuition.service.OktaAuthenticationService;
import com.example.Tuition.service.StudentPaymentService;
import com.example.Tuition.service.StudentService;
import com.okta.authn.sdk.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class StudentControllerImpl implements StudentController {

  private final StudentService studentService;
  private final OktaAuthenticationService oktaAuthenticationService;
  private final StudentPaymentService studentPaymentService;

  public StudentControllerImpl(StudentService studentService, OktaAuthenticationService oktaAuthenticationService, StudentPaymentService studentPaymentService) {
    this.studentService = studentService;
    this.oktaAuthenticationService = oktaAuthenticationService;
    this.studentPaymentService = studentPaymentService;
  }

  @Override
  public ResponseEntity signUpStudent(@RequestBody StudentRequest studentRequest) {
    studentService.signUpStudent(studentRequest);
    return  ResponseUtility.buildOkResponse();
  }

  @Override
  public ResponseEntity login(StudentLoginRequest studentLoginRequest) throws IOException, AuthenticationException {
    return  ResponseUtility.buildOkResponse(oktaAuthenticationService.authenticateUser(studentLoginRequest));

  }

  @Override
  public ResponseEntity getStudentFromBearerToken() {
    String id = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    return ResponseUtility.buildOkResponse(studentService.getUserByUUID(id));
  }

  @Override
  public ResponseEntity setPayment(@PathVariable String id, @RequestBody StripeTokenRequest stripeTokenRequest) throws StripeException {
    studentPaymentService.createStripeCustomerProfile(stripeTokenRequest,id);
    return ResponseUtility.buildOkResponse();
  }


}
