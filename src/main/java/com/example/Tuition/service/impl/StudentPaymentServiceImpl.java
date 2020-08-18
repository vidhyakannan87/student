package com.example.Tuition.service.impl;

import com.example.Tuition.api.request.ChargeRequest;
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
  private final String NO_ACTIVE_PAYMENT_METHOD = "Student  has no active payment method";
  private final String INVALID_STUDENT = "Student does not exist";

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

  @Override
  public void payTuitionFees(String studentId, ChargeRequest chargeRequest) throws StripeException {
    Student student = studentService.getUserByUUID(studentId);

    if (student == null) {
      throw new BadRequestException(INVALID_STUDENT);
    }
     stripeClientService.createStripeCharge(student,chargeRequest.getAmount(),chargeRequest.getStripeToken());

  }

  @Override
  public void updateStripePaymentMethod(StripeTokenRequest stripeTokenRequest, String uuid) throws StripeException {

    Student student = studentService.getUserByUUID(uuid);
    if(student.getStripeCustomerId() == null){
      throw new BadRequestException(NO_ACTIVE_PAYMENT_METHOD);
    }
    stripeClientService.updateStripePaymentMethod(stripeTokenRequest, student);
  }
}
