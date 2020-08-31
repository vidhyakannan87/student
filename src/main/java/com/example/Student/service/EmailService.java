package com.example.Student.service;

import com.example.Student.model.EmailTemplate;

import javax.mail.MessagingException;
import java.util.HashMap;

public interface EmailService {

  void sendEmail(String recipient, EmailTemplate template, HashMap<String, Object> emailVariables) throws MessagingException;

}
