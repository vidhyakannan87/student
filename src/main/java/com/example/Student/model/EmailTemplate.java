package com.example.Student.model;

import lombok.Getter;

@Getter
public enum EmailTemplate {
  RESET_PASSWORD("templates/reset_password.html", "Reset your MyTuitions password");

  private String path;
  private String subject;

  EmailTemplate(String path, String subject) {
    this.path = path;
    this.subject = subject;
  }

}
