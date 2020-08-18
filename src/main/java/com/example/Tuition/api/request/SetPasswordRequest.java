package com.example.Tuition.api.request;

import lombok.Getter;

@Getter
public class SetPasswordRequest {

  private String token;

  private String password;
}
