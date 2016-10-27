package id.co.blogspot.fathan.util;

import org.slf4j.MDC;

/**
 * Created by fathan.mustaqiim on 10/27/2016.
 */
public class Credential {

  public static final String CREDENTIAL_USERNAME = "USERNAME";
  public static final String CREDENTIAL_SESSION_ID = "SESSION_ID";
  public static final String CREDENTIAL_HOSTNAME = "HOSTNAME";

  public static String getUsername() {
    return MDC.get(Credential.CREDENTIAL_USERNAME);
  }

  public static void setUsername(String username) {
    MDC.put(Credential.CREDENTIAL_USERNAME, username);
  }

  public static String getSessionId() {
    return MDC.get(Credential.CREDENTIAL_SESSION_ID);
  }

  public static void setSessionId(String sessionId) {
    MDC.put(Credential.CREDENTIAL_SESSION_ID, sessionId);
  }

  public static String getHostname() {
    return MDC.get(Credential.CREDENTIAL_HOSTNAME);
  }

  public static void setHostname(String hostname) {
    MDC.put(Credential.CREDENTIAL_HOSTNAME, hostname);
  }


}
