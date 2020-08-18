package com.example.Tuition.exception;

import com.example.Tuition.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.BadRequestException;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public ErrorResponse handleBadRequest(BadRequestException e) {

    return buildErrorResponseWithMessage(e.getMessage());
  }


  public ErrorResponse buildErrorResponseWithMessage(String message) {
    return ErrorResponse.builder().message(message).build();
  }

}
