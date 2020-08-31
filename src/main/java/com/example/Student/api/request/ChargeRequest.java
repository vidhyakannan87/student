package com.example.Student.api.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChargeRequest extends StripeTokenRequest {

  float amount;

}
