package com.example.Tuition.service.impl;

import com.example.Tuition.api.request.StripeTokenRequest;
import com.example.Tuition.model.Student;
import com.example.Tuition.service.StripeClientService;
import com.example.Tuition.service.StudentPaymentService;
import com.example.Tuition.service.StudentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;

@Service
public class StudentPaymentServiceImpl implements StudentPaymentService {

 private final StudentService studentService;
 private final StripeClientService stripeClientService;

  private final String STUDENT_HAS_PAYMENT_METHOD = "Student already has an active payment method";

  public StudentPaymentServiceImpl(StudentService studentService, StripeClientService stripeClientService) {
    this.studentService = studentService;
    this.stripeClientService = stripeClientService;
  }

  @Override
  public void createStripeCustomerProfile(StripeTokenRequest stripeTokenRequest, String uuid) throws StripeException {
    Student student = studentService.getUserByUUID(uuid);
    if (student.getStripeCustomerId() != null) {
      throw new BadRequestException(STUDENT_HAS_PAYMENT_METHOD);
    }
    Customer stripeCustomer = stripeClientService.createStripeCustomer(stripeTokenRequest, student);
    student.setStripeCustomerId(stripeCustomer.getId());
    studentService.saveStudent(student);
  }
}
