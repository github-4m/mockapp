package id.co.blogspot.fathan.security.exception;

import org.springframework.security.core.AuthenticationException;

/** Created by fathan.mustaqiim on 10/24/2016. */
public class JwtInvalidAuthenticationTokenException extends AuthenticationException {

  public JwtInvalidAuthenticationTokenException(String msg) {
    super(msg);
  }
}
