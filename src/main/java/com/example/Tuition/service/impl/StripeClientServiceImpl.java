package com.example.Tuition.service.impl;

import com.example.Tuition.api.request.StripeTokenRequest;
import com.example.Tuition.api.request.StudentUpdateRequest;
import com.example.Tuition.model.Student;
import com.example.Tuition.service.StripeClientService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.Tuition.service.utility.StripeConstants.*;
import static com.example.Tuition.service.utility.StripeUtility.convertDollarsToCents;

@Service
public class StripeClientServiceImpl implements StripeClientService {

  @Value("${stripe.apiKey}")
  private String stripeApiKey;

  @Override
  public Customer createStripeCustomer(StripeTokenRequest stripeTokenRequest, Student student) throws StripeException {

    Stripe.apiKey = stripeApiKey;

    Map<String, Object> customerParams = generateStripeCustomerParams(stripeTokenRequest, student);

    return Customer.create(customerParams);

  }

  @Override
  public void createStripeCharge(Student student, float amount, String stripeToken) throws StripeException {

    Stripe.apiKey = stripeApiKey;

    Map<String, Object> chargeParams = generateChargeParams(student, amount, stripeToken);

    Charge.create(chargeParams);


  }

  @Override
  public void updateStripeCustomer(Student student, StudentUpdateRequest studentUpdateRequest) throws StripeException {

    Stripe.apiKey = stripeApiKey;

    Customer customer = Customer.retrieve(student.getStripeCustomerId());

    Map<String, Object> updateParams = generateStripeUpdateCustomerParams(studentUpdateRequest);

    customer.update(updateParams);

  }

  @Override
  public void updateStripePaymentMethod(StripeTokenRequest stripeTokenRequest, Student student) throws StripeException {

    Stripe.apiKey = stripeApiKey;

    Customer stripeCustomer = Customer.retrieve(student.getStripeCustomerId());

    Map<String, Object> chargeParams = generateUpdateStripeCustomerParams(stripeTokenRequest);

    stripeCustomer.update(chargeParams);

  }

  private Map<String, Object> generateStripeUpdateCustomerParams(StudentUpdateRequest studentUpdateRequest) {

    Map<String, Object> params = new HashMap<>();
    params.put(EMAIL, studentUpdateRequest.getEmail());

    return params;
  }

  private Map<String, Object> generateUpdateStripeCustomerParams(StripeTokenRequest stripeTokenRequest) {

    Map<String, Object> params = new HashMap<>();
    params.put(SOURCE, stripeTokenRequest.getStripeToken());

    return params;
  }

  private Map<String, Object> generateStripeCustomerParams(StripeTokenRequest stripeTokenRequest, Student student) {

    Map<String, Object> params = new HashMap<>();
    params.put(EMAIL, student.getEmail());
    params.put(SOURCE, stripeTokenRequest.getStripeToken());

    return params;
  }


  private Map<String, Object> generateChargeParams(Student student, float amount, String stripeToken) {

    Map<String, Object> params = new HashMap<>();

    params.put(AMOUNT, convertDollarsToCents(amount));
    params.put(CURRENCY, USD);
    if (student.getStripeCustomerId() != null) {
      params.put(CUSTOMER, student.getStripeCustomerId());
    } else {
      params.put(SOURCE, stripeToken);
    }

    params.put(DESCRIPTION, "Tuition Fee Payment by Student Id: " + student.getExternalId());
    return params;
  }

}
