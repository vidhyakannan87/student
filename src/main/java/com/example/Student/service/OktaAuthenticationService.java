package com.example.Student.service;


import com.example.Student.api.request.StudentLoginRequest;
import com.example.Student.api.response.AuthenticationResponse;
import com.okta.authn.sdk.AuthenticationException;

import java.io.IOException;

public interface OktaAuthenticationService {

  AuthenticationResponse authenticateUser(StudentLoginRequest studentLoginRequest) throws AuthenticationException, IOException;
}
