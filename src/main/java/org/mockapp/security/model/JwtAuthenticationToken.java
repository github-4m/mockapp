package org.mockapp.security.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

  private String token;

  public JwtAuthenticationToken(String token) {
    super(null, null);
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "JwtAuthenticationToken{" + "token='" + token + '\'' + '}';
  }
}
