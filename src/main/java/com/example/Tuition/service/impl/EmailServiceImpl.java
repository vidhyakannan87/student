package com.example.Tuition.service.impl;

import com.example.Tuition.model.EmailTemplate;
import com.example.Tuition.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@Service
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  @Value("${mail.from}")
  private String fromEmail;

  public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
    this.mailSender = mailSender;
    this.templateEngine = templateEngine;
  }

  @Override
  public void sendEmail(String recipient, EmailTemplate template, HashMap<String, Object> emailVariables) throws MessagingException {

    Context context = new Context();
    context.setVariables(emailVariables);
    String emailBodyText = templateEngine.process(template.getPath(), context);

    MimeMessage mimeMessage = this.mailSender.createMimeMessage();
    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
    message.setSubject(template.getSubject());
    message.setTo(recipient);
    message.setFrom(fromEmail);
    message.setText(emailBodyText, true);

    mailSender.send(mimeMessage);
  }
}
