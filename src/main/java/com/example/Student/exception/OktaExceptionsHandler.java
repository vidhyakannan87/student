package com.example.Student.exception;

import com.example.Student.api.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.okta.sdk.resource.ResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class OktaExceptionsHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ResourceException.class)
  public ErrorResponse handleResourceException(ResourceException e) {

    return ErrorResponse.builder().message(e.getMessage()).build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpClientErrorException.class)
  public ErrorResponse handleHttpClientErrorExceptionException(HttpClientErrorException e) throws JsonProcessingException {

    return ErrorResponse.builder().message(e.getMessage()).build();
  }

}
