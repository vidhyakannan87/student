package com.example.Tuition.service;

import com.example.Tuition.model.EmailTemplate;

import javax.mail.MessagingException;
import java.util.HashMap;

public interface EmailService {

  void sendEmail(String recipient, EmailTemplate template, HashMap<String, Object> emailVariables) throws MessagingException;

}
