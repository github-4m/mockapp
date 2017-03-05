package id.co.blogspot.fathan.dto;

import java.io.Serializable;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
public class AuthenticateRequest implements Serializable {

  private String username;
  private String password;

  public AuthenticateRequest() {
  }

  public AuthenticateRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "AuthenticateRequest{"
        + "username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\''
        + '}';
  }
}
