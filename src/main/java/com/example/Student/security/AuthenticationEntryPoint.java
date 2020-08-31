package com.example.Student.security;

import com.example.Student.api.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Optional;

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  @Override
  public void commence
          (HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
          throws IOException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    PrintWriter writer = response.getWriter();

    Optional<Object> isMessageNull = Optional.ofNullable(request.getAttribute("error"));
    ErrorResponse errorResponse;

    if (isMessageNull.isPresent()) {
      errorResponse = ErrorResponse.builder()
              .timeStamp(new Date().toString())
              .status(HttpStatus.UNAUTHORIZED)
              .message(request.getAttribute("message").toString())
              .build();
    } else {
      errorResponse = ErrorResponse.builder()
              .timeStamp(new Date().toString())
              .status(HttpStatus.UNAUTHORIZED)
              .build();
    }
    ObjectMapper objectMapper = new ObjectMapper();

    writer.println(objectMapper.writeValueAsString(errorResponse));
  }

  @Override
  public void afterPropertiesSet() {
    setRealmName("BPA");
    super.afterPropertiesSet();
  }
}