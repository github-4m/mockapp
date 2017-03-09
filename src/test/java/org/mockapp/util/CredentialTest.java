package org.mockapp.util;

import java.lang.reflect.Constructor;
import java.util.UUID;
import org.junit.Test;

/**
 * Created by fathan.mustaqiim on 11/25/2016.
 */
public class CredentialTest {

  private static final String DEFAULT_HOSTNAME = "HOSTNAME";
  private static final String DEFAULT_REQUEST_ID = UUID.randomUUID().toString();

  @Test
  public void constructorTest() throws Exception {
    Constructor<Credential> credentialConstructor = Credential.class.getDeclaredConstructor();
    credentialConstructor.setAccessible(true);
    credentialConstructor.newInstance();
  }

  @Test
  public void setterTest() throws Exception {
    Credential.setHostname(CredentialTest.DEFAULT_HOSTNAME);
    Credential.setRequestId(CredentialTest.DEFAULT_REQUEST_ID);
  }

  @Test
  public void getterTest() throws Exception {
    Credential.getRequestId();
  }
}
