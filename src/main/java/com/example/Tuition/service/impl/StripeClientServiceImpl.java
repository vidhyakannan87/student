package com.example.Tuition.service.impl;

import com.example.Tuition.api.request.StripeTokenRequest;
import com.example.Tuition.model.Student;
import com.example.Tuition.service.StripeClientService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.Tuition.service.utility.StripeConstants.EMAIL;
import static com.example.Tuition.service.utility.StripeConstants.SOURCE;

@Service
public class StripeClientServiceImpl implements StripeClientService {

  @Value("${stripe.apiKey}")
  private String stripeApiKey;

  @Override
  public Customer createStripeCustomer(StripeTokenRequest stripeTokenRequest, Student student) throws StripeException {

    Stripe.apiKey = stripeApiKey;

    Map<String, Object> chargeParams = generateStripeCustomerParams(stripeTokenRequest, student);

    return Customer.create(chargeParams);

  }


  private Map<String, Object> generateStripeCustomerParams(StripeTokenRequest stripeTokenRequest, Student student) {

    Map<String, Object> params = new HashMap<>();
    params.put(EMAIL, student.getEmail());
    params.put(SOURCE, stripeTokenRequest.getStripeToken());

    return params;
  }

}
