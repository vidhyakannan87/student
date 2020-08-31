package com.example.Student.service.utility;

import java.text.DecimalFormat;

public class StripeUtility {

  public static int convertDollarsToCents(Float cost) {

    return Float.valueOf(cost * 100).intValue();
  }

  public static float convertCentsToDollars(int charge) {

    float dollarCost = Float.valueOf(charge) / 100.0f;

    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    return Float.valueOf(decimalFormat.format(dollarCost));

  }

  public static int convertRupeesToPaise(Float cost) {

    return Float.valueOf(cost * 100).intValue();
  }



}