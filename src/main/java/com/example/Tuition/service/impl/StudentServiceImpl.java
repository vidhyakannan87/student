package com.example.Tuition.service.impl;


import com.example.Tuition.api.request.StudentRequest;
import com.example.Tuition.model.Student;
import com.example.Tuition.repository.StudentRepository;
import com.example.Tuition.service.OktaUserService;
import com.example.Tuition.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.example.Tuition.service.utility.UniqueIdGenerator.generateBase64UUID;

@Service
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;
  private final OktaUserService oktaUserService;
  private final ModelMapper modelMapper = new ModelMapper();


  public StudentServiceImpl(StudentRepository studentRepository, OktaUserService oktaUserService) {
    this.studentRepository = studentRepository;
    this.oktaUserService = oktaUserService;
  }

  @Override
  public void signUpStudent(StudentRequest studentRequest) {

    Student student = modelMapper.map(studentRequest, Student.class);

    String oktaId = oktaUserService.createUser(studentRequest);

    student.setUuid(generateBase64UUID(oktaId));
    student.setExternalId(oktaId);

    studentRepository.save(student);
  }
}
