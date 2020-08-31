package com.example.Student.controller.impl;

import com.example.Student.api.request.*;
import com.example.Student.api.response.AuthenticationResponse;
import com.example.Student.controller.StudentController;
import com.example.Student.service.OktaAuthenticationService;
import com.example.Student.service.StudentPaymentService;
import com.example.Student.service.StudentService;
import com.okta.authn.sdk.AuthenticationException;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
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
    return ResponseUtility.buildOkResponse();
  }

  @Override
  public ResponseEntity login(StudentLoginRequest studentLoginRequest) throws IOException, AuthenticationException {
    AuthenticationResponse authenticationResponse = oktaAuthenticationService.authenticateUser(studentLoginRequest);
    return ResponseUtility.buildOkResponse(authenticationResponse);

  }

  @Override
  public ResponseEntity getStudentFromBearerToken() {
    String id = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    return ResponseUtility.buildOkResponse(studentService.getUserByUUID(id));
  }

  @Override
  public ResponseEntity setStripePaymentProfile(@PathVariable String id, @RequestBody StripeTokenRequest stripeTokenRequest) throws StripeException {
    studentPaymentService.createStripeCustomerProfile(stripeTokenRequest, id);
    return ResponseUtility.buildOkResponse();
  }

  @Override
  public ResponseEntity setRazorPayProfile(@PathVariable String id) throws RazorpayException {
    studentPaymentService.createRazorPayCustomerProfile(id);
    return ResponseUtility.buildOkResponse();
  }

  @Override
  public ResponseEntity payTuitionFeesViaStripe(@PathVariable String id, @RequestBody ChargeRequest chargeRequest) throws StripeException, AuthenticationException, IOException {
    studentPaymentService.payTuitionFees(id, chargeRequest);
    return ResponseUtility.buildOkResponse();
  }

  @Override
  public ResponseEntity updateStudentProfile(@PathVariable String id, @RequestBody StudentUpdateRequest studentUpdateRequest) throws StripeException, RazorpayException {
    studentService.updateStudent(id, studentUpdateRequest);
    return ResponseUtility.buildOkResponse();
  }

  @Override
  public ResponseEntity changePassword(@PathVariable String id, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
    studentService.changePassword(id, updatePasswordRequest);
    return ResponseUtility.buildOkResponse();
  }

  @Override
  public ResponseEntity resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws MessagingException {
    studentService.resetPassword(resetPasswordRequest);
    return ResponseUtility.buildOkResponse();

  }

  @Override
  public ResponseEntity updatePassword(@RequestBody SetPasswordRequest setPasswordRequest) {
    studentService.updatePassword(setPasswordRequest);
    return ResponseUtility.buildOkResponse();
  }

  @Override
  public ResponseEntity updatePaymentMethod(@PathVariable String id, @RequestBody StripeTokenRequest stripeTokenRequest) throws StripeException {
    studentPaymentService.updateStripePaymentMethod(stripeTokenRequest, id);
    return ResponseUtility.buildOkResponse();
  }

  @Override
  public ResponseEntity payTuitionFeesViaRazorPay(@PathVariable String id, @RequestBody ChargeRequest chargeRequest) throws RazorpayException {
    studentPaymentService.createPaymentLink(id, chargeRequest);
    return ResponseUtility.buildOkResponse();
  }

  @Override
  public ResponseEntity createRazorPaySubscription(@PathVariable String id, @RequestBody RazorPaySubscriptionRequest subscriptionRequest) throws RazorpayException {
    studentPaymentService.createSubscriptionLink(id, subscriptionRequest);
    return ResponseUtility.buildOkResponse();
  }


}
