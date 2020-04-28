package com.example.Tuition.config;

import com.nimbusds.oauth2.sdk.ParseException;
import com.okta.authn.sdk.client.AuthenticationClient;
import com.okta.authn.sdk.client.AuthenticationClients;
import com.okta.jwt.JwtHelper;
import com.okta.jwt.JwtVerifier;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.ClientBuilder;
import com.okta.sdk.client.Clients;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class OktaConfig {
  @Getter
  @Value("${okta.issuerId}")
  private String oktaIssuerId;

  @Value("${okta.audience}")
  private String oktaAudience;

  @Getter
  @Value("${okta.clientId}")
  private String oktaClientId;

  @Getter
  @Value("${okta.apiKey}")
  private String oktaApiKey;

  @Getter
  @Value("${okta.url}")
  private String oktaOrgUrl;

  @Value("${okta.basicAuth}")
  @Getter
  private String oktaBasicAuth;

  @Bean
  public JwtVerifier jwtVerifier() throws IOException, ParseException {
    return new JwtHelper()
            .setIssuerUrl(oktaIssuerId)
            .setAudience(oktaAudience)  // defaults to 'api://default'
            .setClientId(oktaClientId) // optional
            .build();
  }

  @Bean
  public Client client() {
    TokenClientCredentials tokenClientCredentials = new TokenClientCredentials(oktaApiKey);
    ClientBuilder builder = Clients.builder();
    return builder
            .setClientCredentials(tokenClientCredentials)
            .setOrgUrl(oktaOrgUrl)
            .build();
  }

  @Bean
  public AuthenticationClient authenticationClient() {

    AuthenticationClient client = AuthenticationClients.builder().setOrgUrl(oktaOrgUrl).build();

    return client;
  }
}
