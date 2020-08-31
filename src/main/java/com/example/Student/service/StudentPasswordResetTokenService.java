package com.example.Student.service;

import com.example.Student.model.StudentPasswordResetToken;

public interface StudentPasswordResetTokenService {

  void savePasswordResetToken(StudentPasswordResetToken passwordResetToken);

  StudentPasswordResetToken loadPasswordResetTokenByToken(String token);

  void deletePasswordResetTokenByToken(StudentPasswordResetToken passwordResetToken);

}
