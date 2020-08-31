package com.example.Student.service;

import com.example.Student.api.request.ChargeRequest;
import com.example.Student.api.request.RazorPaySubscriptionRequest;
import com.example.Student.api.request.StripeTokenRequest;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface StudentPaymentService {

  void createStripeCustomerProfile(StripeTokenRequest stripeTokenRequest, String uuid) throws StripeException;

  void payTuitionFees(String studentId, ChargeRequest chargeRequest) throws StripeException;

  void updateStripePaymentMethod(StripeTokenRequest stripeTokenRequest,String uuid) throws StripeException;

  void createRazorPayCustomerProfile(String uuid) throws RazorpayException;

  void createPaymentLink(String studentId, ChargeRequest chargeRequest) throws RazorpayException;

  void createSubscriptionLink(String studentId, RazorPaySubscriptionRequest subscriptionRequest) throws RazorpayException;

}
