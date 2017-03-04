package id.co.blogspot.fathan.security.exception;

import org.springframework.security.core.AuthenticationException;

/** Created by fathan.mustaqiim on 10/27/2016. */
public class UnauthorizedException extends AuthenticationException {

  public UnauthorizedException(String msg) {
    super(msg);
  }
}
