package com.example.Tuition.service;

import com.example.Tuition.api.request.RazorPaySubscriptionRequest;
import com.example.Tuition.api.request.StudentUpdateRequest;
import com.example.Tuition.model.Student;
import com.razorpay.*;

public interface RazorPayClientService {

  Customer createRazorPayCustomer(Student student) throws RazorpayException;

  void updateRazorPayCustomer(Student student, StudentUpdateRequest studentUpdateRequest) throws RazorpayException;

  Invoice createPaymentLink(Student student, float amount) throws RazorpayException;

  Plan createRazorPayPlan(String period,float amount,int interval) throws RazorpayException;

  Subscription createSubscriptionLink(Student student, RazorPaySubscriptionRequest subscriptionRequest) throws RazorpayException;

}
