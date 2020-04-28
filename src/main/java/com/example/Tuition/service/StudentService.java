package com.example.Tuition.service;

import com.example.Tuition.api.request.StudentRequest;
import com.example.Tuition.model.Student;

public interface StudentService {

  void signUpStudent(StudentRequest studentRequest);

  Student getUserByUUID(String id);


}
