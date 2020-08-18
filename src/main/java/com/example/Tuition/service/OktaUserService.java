package com.example.Tuition.service;

import com.example.Tuition.api.request.SetPasswordRequest;
import com.example.Tuition.api.request.StudentRequest;
import com.example.Tuition.api.request.StudentUpdateRequest;
import com.example.Tuition.api.request.UpdatePasswordRequest;
import com.example.Tuition.model.Student;

public interface OktaUserService {

  String createUser(StudentRequest studentRequest);

  void updateStudentOktaProfile(Student student, StudentUpdateRequest studentRequest);

  void changePassword(Student student, UpdatePasswordRequest updatePasswordRequest);

  void setPassword(Student student, SetPasswordRequest setPasswordRequest);
}
