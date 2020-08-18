package com.example.Tuition.service.utility;


import com.example.Tuition.model.Student;
import com.example.Tuition.model.StudentPasswordResetToken;

public class PasswordResetUtility {


  public static String createResetUrl(String contextPath, String token) {

    return contextPath + "/set-password?token=" + token;
  }

  public static StudentPasswordResetToken createPasswordResetTokenForStudent(Student student, String token) {

    StudentPasswordResetToken passwordResetToken = new StudentPasswordResetToken();
    passwordResetToken.setToken(token);
    passwordResetToken.setStudent(student);

    return passwordResetToken;
  }


}
