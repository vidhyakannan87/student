package com.example.Student.service.impl;

import com.example.Student.model.StudentPasswordResetToken;
import com.example.Student.service.StudentPasswordResetTokenService;
import com.example.Student.repository.StudentPasswordResetTokenRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class StudentPasswordResetTokenServiceImpl implements StudentPasswordResetTokenService {

  private  final StudentPasswordResetTokenRepository studentPasswordResetTokenRepository;

  private final String TOKEN_NOT_FOUND = "Token Not Found";

  public StudentPasswordResetTokenServiceImpl(StudentPasswordResetTokenRepository studentPasswordResetTokenRepository) {
    this.studentPasswordResetTokenRepository = studentPasswordResetTokenRepository;
  }

  @Override
  public void savePasswordResetToken(StudentPasswordResetToken passwordResetToken) {
    studentPasswordResetTokenRepository.saveAndFlush(passwordResetToken);

  }

  @Override
  public StudentPasswordResetToken loadPasswordResetTokenByToken(String token) {
    StudentPasswordResetToken studentPasswordResetToken = studentPasswordResetTokenRepository.findByToken(token);

    if (studentPasswordResetToken == null) {
      throw new EntityNotFoundException(TOKEN_NOT_FOUND);
    }

    return studentPasswordResetToken;
  }

  @Override
  public void deletePasswordResetTokenByToken(StudentPasswordResetToken passwordResetToken) {
    studentPasswordResetTokenRepository.delete(passwordResetToken);
  }
}
