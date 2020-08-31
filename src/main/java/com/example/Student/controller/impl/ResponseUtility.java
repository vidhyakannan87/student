package com.example.Student.controller.impl;

import org.springframework.http.ResponseEntity;

import java.net.URI;

public class ResponseUtility {

  public static ResponseEntity buildOkResponse() {

    return ResponseEntity.ok().build();
  }

  public static ResponseEntity buildOkResponse(Object entity) {

    return ResponseEntity.ok().body(entity);
  }

  public static ResponseEntity buildCreatedResponse(URI location) {

    return ResponseEntity.created(location).build();
  }
}
