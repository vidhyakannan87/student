package com.example.Tuition.service;

import com.example.Tuition.api.request.StripeTokenRequest;
import com.stripe.exception.StripeException;

public interface StudentPaymentService {

  void createStripeCustomerProfile(StripeTokenRequest stripeTokenRequest, String uuid) throws StripeException;

}
