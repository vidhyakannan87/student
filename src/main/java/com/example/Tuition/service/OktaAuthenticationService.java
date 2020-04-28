package com.example.Tuition.service;


import com.example.Tuition.api.request.StudentLoginRequest;
import com.example.Tuition.api.response.AuthenticationResponse;
import com.okta.authn.sdk.AuthenticationException;

import java.io.IOException;

public interface OktaAuthenticationService {

  AuthenticationResponse authenticateUser(StudentLoginRequest studentLoginRequest) throws AuthenticationException, IOException;
}
