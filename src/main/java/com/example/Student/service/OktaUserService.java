package com.example.Student.service;

import com.example.Student.api.request.StudentRequest;
import com.example.Student.api.request.StudentUpdateRequest;
import com.example.Student.api.request.UpdatePasswordRequest;
import com.example.Student.model.Student;
import com.example.Student.api.request.SetPasswordRequest;

public interface OktaUserService {

  String createUser(StudentRequest studentRequest);

  void updateStudentOktaProfile(Student student, StudentUpdateRequest studentRequest);

  void changePassword(Student student, UpdatePasswordRequest updatePasswordRequest);

  void setPassword(Student student, SetPasswordRequest setPasswordRequest);
}
