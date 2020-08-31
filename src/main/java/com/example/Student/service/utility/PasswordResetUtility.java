package com.example.Student.service.utility;


import com.example.Student.model.Student;
import com.example.Student.model.StudentPasswordResetToken;

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
