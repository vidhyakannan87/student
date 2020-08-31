package com.example.Student.service;

import com.example.Student.api.request.*;
import com.example.Student.model.Student;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

import javax.mail.MessagingException;

public interface StudentService {

  void signUpStudent(StudentRequest studentRequest);

  Student getUserByUUID(String id);

  Student getStudentByEmail(String email);

  void saveStudent(Student student);

  void updateStudent(String uuid, StudentUpdateRequest studentUpdateRequest) throws StripeException, RazorpayException;

  void changePassword(String uuid, UpdatePasswordRequest updatePasswordRequest);

  void resetPassword(ResetPasswordRequest resetPasswordRequest) throws MessagingException;

  void updatePassword(SetPasswordRequest setPasswordRequest);

}
