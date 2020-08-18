package com.example.Tuition.api.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChargeRequest extends StripeTokenRequest {

  float amount;

}
