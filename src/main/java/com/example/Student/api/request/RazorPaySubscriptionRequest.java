package com.example.Student.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RazorPaySubscriptionRequest {

  private int totalCount;

  private int quantity;

  private String name;

  private String period;

  private int interval;

  private float amount;

  private float upFrontAmount;

}
