package com.example.Student.service.impl;


import com.example.Student.api.request.*;
import com.example.Student.model.EmailTemplate;
import com.example.Student.model.Student;
import com.example.Student.model.StudentPasswordResetToken;
import com.example.Student.repository.StudentRepository;
import com.example.Student.service.*;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.stream.Collectors;

import static com.example.Student.service.utility.PasswordResetUtility.createPasswordResetTokenForStudent;
import static com.example.Student.service.utility.PasswordResetUtility.createResetUrl;
import static com.example.Student.service.utility.UniqueIdGenerator.generateBase64UUID;
import static com.example.Student.service.utility.UniqueIdGenerator.generateRandomUUID;

@Service
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;
  private final OktaUserService oktaUserService;
  private final EmailService emailService;
  private final StudentPasswordResetTokenService studentPasswordResetTokenService;
  private final StripeClientService stripeClientService;
  private final RazorPayClientService razorPayClientService;

  private final ModelMapper modelMapper = new ModelMapper();

  @Value("${mail.baseUrl}")
  private String baseUrl;


  public StudentServiceImpl(StudentRepository studentRepository, OktaUserService oktaUserService, EmailService emailService, StudentPasswordResetTokenService studentPasswordResetTokenService, StripeClientService stripeClientService, RazorPayClientService razorPayClientService) {
    this.studentRepository = studentRepository;
    this.oktaUserService = oktaUserService;
    this.emailService = emailService;
    this.studentPasswordResetTokenService = studentPasswordResetTokenService;
    this.stripeClientService = stripeClientService;
    this.razorPayClientService = razorPayClientService;
  }

  @Override
  public void signUpStudent(StudentRequest studentRequest) {

    Student student = modelMapper.map(studentRequest, Student.class);

    String oktaId = oktaUserService.createUser(studentRequest);

    student.setUuid(generateBase64UUID(oktaId));
    student.setExternalId(oktaId);
    student.getInterests().stream().peek(interest -> interest.setStudent(student)).collect(Collectors.toList());
    saveStudent(student);
  }

  @Override
  public Student getUserByUUID(String id) {
    return studentRepository.findByUuid(id);
  }


  @Override
  public void saveStudent(Student student) {
    studentRepository.saveAndFlush(student);
  }

  @Override
  public void updateStudent(String uuid, StudentUpdateRequest studentRequest) throws StripeException, RazorpayException {
    Student existingStudent = getUserByUUID(uuid);

    if (!existingStudent.getFirstName().equals(studentRequest.getFirstName())) {
      existingStudent.setFirstName(studentRequest.getFirstName());
    }
    if (!existingStudent.getLastName().equals(studentRequest.getLastName())) {
      existingStudent.setLastName(studentRequest.getLastName());
    }
    if (!existingStudent.getDateOfBirth().isEqual(studentRequest.getDateOfBirth())) {
      existingStudent.setDateOfBirth(studentRequest.getDateOfBirth());
    }
    if (!existingStudent.getEducationBoard().equals(studentRequest.getEducationBoard())) {
      existingStudent.setEducationBoard(studentRequest.getEducationBoard());
    }

    if (existingStudent.getRazorPayCustomerId() != null && (!existingStudent.getUserName().equals(studentRequest.getUserName()) || !existingStudent.getPhoneNumber().equals(studentRequest.getPhoneNumber()))) {
      razorPayClientService.updateRazorPayCustomer(existingStudent, studentRequest);
    }

    if (!existingStudent.getPhoneNumber().equals(studentRequest.getPhoneNumber())) {
      existingStudent.setPhoneNumber(studentRequest.getPhoneNumber());
    }
    if (!existingStudent.getUserName().equals(studentRequest.getUserName())) {
      oktaUserService.updateStudentOktaProfile(existingStudent, studentRequest);
      stripeClientService.updateStripeCustomer(existingStudent, studentRequest);
      existingStudent.setUserName(studentRequest.getUserName());
      existingStudent.setEmail(studentRequest.getEmail());
    }


    if (!studentRequest.getOldPassword().equals(studentRequest.getNewPassword())) {
      UpdatePasswordRequest updatePasswordRequest = modelMapper.map(studentRequest, UpdatePasswordRequest.class);
      oktaUserService.changePassword(existingStudent, updatePasswordRequest);
    }


    if (!existingStudent.getInterests().equals(studentRequest.getInterests())) {
      existingStudent.setInterests(studentRequest.getInterests());
      existingStudent.getInterests().stream().peek(interest -> interest.setStudent(existingStudent)).collect(Collectors.toList());
    }

    saveStudent(existingStudent);

  }

  @Override
  public void changePassword(String uuid, UpdatePasswordRequest updatePasswordRequest) {
    Student student = getUserByUUID(uuid);
    oktaUserService.changePassword(student, updatePasswordRequest);
  }

  @Override
  public void resetPassword(ResetPasswordRequest resetPasswordRequest) throws MessagingException {
    Student student = getStudentByEmail(resetPasswordRequest.getEmail());
    String token = generateRandomUUID();

    StudentPasswordResetToken passwordResetToken = createPasswordResetTokenForStudent(student, token);
    studentPasswordResetTokenService.savePasswordResetToken(passwordResetToken);

    HashMap<String, Object> emailVariables = new HashMap<>();
    emailVariables.put("url", createResetUrl(baseUrl, token));
    emailVariables.put("firstName", student.getFirstName());

    emailService.sendEmail(student.getEmail(), EmailTemplate.RESET_PASSWORD, emailVariables);


  }

  @Override
  public void updatePassword(SetPasswordRequest setPasswordRequest) {
    StudentPasswordResetToken studentPasswordResetToken = studentPasswordResetTokenService.
            loadPasswordResetTokenByToken(setPasswordRequest.getToken());

    oktaUserService.setPassword(studentPasswordResetToken.getStudent(), setPasswordRequest);

    studentPasswordResetTokenService.deletePasswordResetTokenByToken(studentPasswordResetToken);

  }

  @Override
  public Student getStudentByEmail(String email) {
    return studentRepository.findByEmail(email);
  }


}
