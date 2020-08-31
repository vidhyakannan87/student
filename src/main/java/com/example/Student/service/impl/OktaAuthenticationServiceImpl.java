package com.example.Student.service.impl;

import com.example.Student.api.request.StudentLoginRequest;
import com.example.Student.api.response.AuthenticationResponse;
import com.example.Student.config.OktaConfig;
import com.example.Student.service.OktaAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okta.authn.sdk.AuthenticationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Service
public class OktaAuthenticationServiceImpl implements OktaAuthenticationService {

  private final OktaConfig oktaConfig;

  private final String URL_PATH = "/v1/token";
  private final String GRANT_TYPE_KEY = "grant_type";
  private final String GRANT_TYPE_PASSWORD_VALUE = "password";
  private final String SCOPE_KEY = "scope";
  private final String SCOPE_LOGIN_VALUE = "openid offline_access profile";
  private final String USERNAME_KEY = "username";
  private final String PASSWORD_KEY = "password";

  public OktaAuthenticationServiceImpl(OktaConfig oktaConfig) {
    this.oktaConfig = oktaConfig;
  }

  @Override
  public AuthenticationResponse authenticateUser(StudentLoginRequest studentLoginRequest) throws AuthenticationException, IOException {
    String url = buildUrl();
    MultiValueMap<String, Object> map = buildAuthenticationRequestMap(studentLoginRequest);
    AuthenticationResponse oktaResponse = doPost(url, map);

    return oktaResponse;

  }

  private String buildUrl() {

    String baseUrl = oktaConfig.getOktaIssuerId();
    UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
    urlBuilder.path(URL_PATH);

    return urlBuilder.build(true).encode().toUriString();
  }

  private MultiValueMap<String, Object> buildAuthenticationRequestMap(StudentLoginRequest studentLoginRequest) {

    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add(GRANT_TYPE_KEY, GRANT_TYPE_PASSWORD_VALUE);
    map.add(SCOPE_KEY, SCOPE_LOGIN_VALUE);
    map.add(USERNAME_KEY, studentLoginRequest.getUsername());
    map.add(PASSWORD_KEY, studentLoginRequest.getPassword());

    return map;
  }

  private AuthenticationResponse doPost(String url, MultiValueMap map) throws IOException {

    HttpEntity<MultiValueMap> entity = new HttpEntity<>(map, getHttpEntity());
    RestTemplate restTemplate = new RestTemplate();
    String oktaResponse = restTemplate.postForObject(url, entity, String.class);

    return buildOktaAuthenticationResponse(oktaResponse);
  }

  private HttpHeaders getHttpEntity() {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add(HttpHeaders.AUTHORIZATION, oktaConfig.getOktaBasicAuth());

    return headers;
  }

  private AuthenticationResponse buildOktaAuthenticationResponse(String oktaResponse) throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    AuthenticationResponse oktaAuthenticationResponse = mapper.readValue(oktaResponse, AuthenticationResponse.class);

    return oktaAuthenticationResponse;
  }

}
