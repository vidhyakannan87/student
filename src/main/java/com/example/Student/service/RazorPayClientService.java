package com.example.Student.service;

import com.example.Student.model.Student;
import com.example.Student.api.request.RazorPaySubscriptionRequest;
import com.example.Student.api.request.StudentUpdateRequest;
import com.razorpay.*;

public interface RazorPayClientService {

  Customer createRazorPayCustomer(Student student) throws RazorpayException;

  void updateRazorPayCustomer(Student student, StudentUpdateRequest studentUpdateRequest) throws RazorpayException;

  Invoice createPaymentLink(Student student, float amount) throws RazorpayException;

  Plan createRazorPayPlan(String period,float amount,int interval) throws RazorpayException;

  Subscription createSubscriptionLink(Student student, RazorPaySubscriptionRequest subscriptionRequest) throws RazorpayException;

}
