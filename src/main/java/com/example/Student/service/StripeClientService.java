package com.example.Student.service;

import com.example.Student.model.Student;
import com.example.Student.api.request.StripeTokenRequest;
import com.example.Student.api.request.StudentUpdateRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

public interface StripeClientService {

  Customer createStripeCustomer(StripeTokenRequest stripeTokenRequest, Student student) throws StripeException;

  void createStripeCharge(Student student, float amount, String stripeToken) throws StripeException;

  void updateStripeCustomer(Student student, StudentUpdateRequest studentUpdateRequest) throws StripeException;

  void updateStripePaymentMethod(StripeTokenRequest stripeTokenRequest, Student student) throws  StripeException;

}
