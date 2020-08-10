package com.example.Tuition.service;

import com.example.Tuition.api.request.StripeTokenRequest;
import com.example.Tuition.model.Student;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

public interface StripeClientService {

  Customer createStripeCustomer(StripeTokenRequest stripeTokenRequest, Student student) throws StripeException;

}
