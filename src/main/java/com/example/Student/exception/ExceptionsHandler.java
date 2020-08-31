package com.example.Student.exception;

import com.example.Student.api.response.ErrorResponse;
import com.razorpay.RazorpayException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.BadRequestException;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public ErrorResponse handleBadRequest(BadRequestException e) {

    return buildErrorResponseWithMessage(e.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(RazorpayException.class)
  public ErrorResponse handleRazorPayException(HttpClientErrorException e) {

    return ErrorResponse.builder().message(e.getMessage()).build();
  }


  public ErrorResponse buildErrorResponseWithMessage(String message) {
    return ErrorResponse.builder().message(message).build();
  }

}
