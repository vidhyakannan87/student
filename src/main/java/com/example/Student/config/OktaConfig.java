package com.example.Student.config;

import com.okta.authn.sdk.client.AuthenticationClient;
import com.okta.authn.sdk.client.AuthenticationClients;
import com.okta.jwt.AccessTokenVerifier;
import com.okta.jwt.JwtVerifiers;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.ClientBuilder;
import com.okta.sdk.client.Clients;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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


  public AccessTokenVerifier jwtVerifier() {
    return JwtVerifiers.accessTokenVerifierBuilder()
            .setIssuer(oktaIssuerId)
            .setAudience(oktaAudience)
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
