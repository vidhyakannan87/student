package com.example.Tuition.service.utility;

import java.util.Base64;
import java.util.UUID;

public class UniqueIdGenerator {

  public static <T> String generateBase64UUID(T Object) {

    UUID uuid = UUID.nameUUIDFromBytes(Object.toString().getBytes());
    Base64.Encoder encoder = Base64.getUrlEncoder();
    return encoder.encodeToString(uuid.toString().getBytes());
  }

  public static String generateRandomUUID() {
    return UUID.randomUUID().toString();
  }
}
