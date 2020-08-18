package com.example.Tuition.controller;

import com.example.Tuition.api.request.*;
import com.okta.authn.sdk.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RequestMapping("/students")
public interface StudentController {

  @PostMapping("/sign-up")
  ResponseEntity signUpStudent(@RequestBody StudentRequest studentRequest);

  @PostMapping("/login")
  ResponseEntity login(@RequestBody StudentLoginRequest studentLoginRequest) throws IOException, AuthenticationException;

  @GetMapping("/me")
  ResponseEntity getStudentFromBearerToken();

  @PostMapping(value = "/{id}/setPaymentProfile")
  @PreAuthorize("#id == authentication.name")
  ResponseEntity setPaymentProfile(@PathVariable String id, @RequestBody StripeTokenRequest stripeTokenRequest) throws StripeException, AuthenticationException, IOException;


  @PostMapping(value = "/{id}/payment")
  @PreAuthorize("#id == authentication.name")
  ResponseEntity payTuitionFees(@PathVariable String id, @RequestBody ChargeRequest chargeRequest) throws StripeException, AuthenticationException, IOException;


  @PutMapping(value = "/{id}")
  @PreAuthorize("#id == authentication.name")
  ResponseEntity updateStudentProfile(@PathVariable String id, @RequestBody StudentUpdateRequest studentUpdateRequest) throws StripeException;

  @PatchMapping(value = "/{id}/password")
  @PreAuthorize("#id == authentication.name")
  ResponseEntity changePassword(@PathVariable String id,@RequestBody UpdatePasswordRequest updatePasswordRequest);

  @PostMapping(value = "/resetPassword")
  ResponseEntity resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws MessagingException;


  @PostMapping(value = "/updatePassword")
  ResponseEntity updatePassword(@RequestBody SetPasswordRequest setPasswordRequest);


  @PutMapping(value = "/{id}/updatePaymentProfile")
  @PreAuthorize("#id == authentication.name")
  ResponseEntity updatePaymentMethod(@PathVariable String id, @RequestBody StripeTokenRequest stripeTokenRequest) throws StripeException;


}
