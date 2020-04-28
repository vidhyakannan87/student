package com.example.Tuition.model;

public enum Role {

  STUDENT(Names.STUDENT);

  private String value;

  Role(String value) {

    this.value = value;
  }

  public static class Names {

    public final static String STUDENT = "tuitionStudent";
  }
}