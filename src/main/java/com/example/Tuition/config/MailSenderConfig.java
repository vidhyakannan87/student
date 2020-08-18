package com.example.Tuition.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSenderConfig {
  @Value("${mail.username}")
  private String username;

  @Value("${mail.password}")
  private String password;

  @Value("${mail.host}")
  private String host;

  @Value("${mail.port}")
  private int port;

  @Value("${mail.debug}")
  private String mailDebug;

  private final String MAIL_PROTOCOL = "mail.transport.protocol";
  private final String MAIL_AUTH = "mail.smtp.auth";
  private final String MAIL_TTLS_REQUIRED = "mail.smtp.starttls.required";
  private final String MAIL_TTLS = "mail.smtp.starttls.enable";
  private final String MAIL_DEBUG = "mail.debug";

  @Bean
  public JavaMailSender javaMailSender() {

    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();
    props.put(MAIL_PROTOCOL, "smtp");
    props.put(MAIL_AUTH, "true");
    props.put(MAIL_TTLS, "true");
    props.put(MAIL_TTLS_REQUIRED,"true");
    props.put(MAIL_DEBUG, mailDebug);

    return mailSender;
  }
}
