package com.example.Student.service.impl;

import com.example.Student.model.Student;
import com.example.Student.model.StudentInvoice;
import com.example.Student.service.StripeClientService;
import com.example.Student.api.request.ChargeRequest;
import com.example.Student.api.request.RazorPaySubscriptionRequest;
import com.example.Student.api.request.StripeTokenRequest;
import com.example.Student.repository.StudentInvoiceRepository;
import com.example.Student.service.RazorPayClientService;
import com.example.Student.service.StudentPaymentService;
import com.example.Student.service.StudentService;
import com.razorpay.Invoice;
import com.razorpay.RazorpayException;
import com.razorpay.Subscription;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;

import static com.example.Student.service.utility.RazorPayConstants.RAZORPAYID;

@Service
public class StudentPaymentServiceImpl implements StudentPaymentService {

 private final StudentService studentService;
 private final StripeClientService stripeClientService;
 private final RazorPayClientService razorPayClientService;
 private final StudentInvoiceRepository studentInvoiceRepository;

  private final String STUDENT_HAS_PAYMENT_METHOD = "Student already has an active payment method";
  private final String NO_ACTIVE_PAYMENT_METHOD = "Student  has no active payment method";
  private final String INVALID_STUDENT = "Student does not exist";
  private final String STUDENT_HAS_RAZORPAY_ID = "Student is already added to Razor Pay";

  public StudentPaymentServiceImpl(StudentService studentService, StripeClientService stripeClientService, RazorPayClientService razorPayClientService, StudentInvoiceRepository studentInvoiceRepository) {
    this.studentService = studentService;
    this.stripeClientService = stripeClientService;
    this.razorPayClientService = razorPayClientService;
    this.studentInvoiceRepository = studentInvoiceRepository;
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

  @Override
  public void createRazorPayCustomerProfile(String uuid) throws RazorpayException {
    Student student = studentService.getUserByUUID(uuid);
    if (student.getRazorPayCustomerId()!= null) {
      throw new BadRequestException(STUDENT_HAS_RAZORPAY_ID);
    }

    com.razorpay.Customer razorPayCustomer = razorPayClientService.createRazorPayCustomer(student);
    student.setRazorPayCustomerId(razorPayCustomer.get(RAZORPAYID));
    studentService.saveStudent(student);
  }

  @Override
  public void createPaymentLink(String studentId, ChargeRequest chargeRequest) throws RazorpayException {
    Student student = studentService.getUserByUUID(studentId);

    if (student == null) {
      throw new BadRequestException(INVALID_STUDENT);
    }
    Invoice invoice = razorPayClientService.createPaymentLink(student,chargeRequest.getAmount());
    StudentInvoice studentInvoice = new StudentInvoice();
    studentInvoice.setStudent(student);
    studentInvoice.setInvoiceId(invoice.get(RAZORPAYID));
    studentInvoiceRepository.saveAndFlush(studentInvoice);
  }

  @Override
  public void createSubscriptionLink(String studentId, RazorPaySubscriptionRequest subscriptionRequest) throws RazorpayException {
    Student student = studentService.getUserByUUID(studentId);
    if (student == null) {
      throw new BadRequestException(INVALID_STUDENT);
    }
    Subscription subscription = razorPayClientService.createSubscriptionLink(student,subscriptionRequest);
    StudentInvoice studentInvoice = new StudentInvoice();
    studentInvoice.setStudent(student);
    studentInvoice.setSubscriptionId(subscription.get(RAZORPAYID));
    studentInvoiceRepository.saveAndFlush(studentInvoice);


  }
}
