package com.example.Tuition.service;

import com.example.Tuition.model.StudentPasswordResetToken;

public interface StudentPasswordResetTokenService {

  void savePasswordResetToken(StudentPasswordResetToken passwordResetToken);

  StudentPasswordResetToken loadPasswordResetTokenByToken(String token);

  void deletePasswordResetTokenByToken(StudentPasswordResetToken passwordResetToken);

}
