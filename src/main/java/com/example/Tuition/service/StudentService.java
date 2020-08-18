package com.example.Tuition.service;

import com.example.Tuition.api.request.*;
import com.example.Tuition.model.Student;
import com.stripe.exception.StripeException;

import javax.mail.MessagingException;

public interface StudentService {

  void signUpStudent(StudentRequest studentRequest);

  Student getUserByUUID(String id);

  Student getStudentByEmail(String email);

  void saveStudent(Student student);

  void updateStudent(String uuid, StudentUpdateRequest studentUpdateRequest) throws StripeException;

  void changePassword(String uuid, UpdatePasswordRequest updatePasswordRequest);

  void resetPassword(ResetPasswordRequest resetPasswordRequest) throws MessagingException;

  void updatePassword(SetPasswordRequest setPasswordRequest);

}
