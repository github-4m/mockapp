package org.mockapp.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * Created by 4than.mustaqiim on 3/9/2017.
 */
public final class Generator {

  private Generator() {
  }

  public static String generateCode() throws Exception {
    String base = UUID.randomUUID().toString();
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    return (new HexBinaryAdapter())
        .marshal(messageDigest.digest(base.getBytes(StandardCharsets.UTF_8)));
  }

}
