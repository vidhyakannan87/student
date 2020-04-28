package com.example.Tuition.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("token_type")
  private String tokenType;

  @JsonProperty("expires_in")
  private long expiresIn;

  @JsonProperty(value = "scope", access = JsonProperty.Access.WRITE_ONLY)
  private String scope;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty(value = "id_token", access = JsonProperty.Access.WRITE_ONLY)
  private String idToken;

}
