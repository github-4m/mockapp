package org.mockapp.security;

import io.jsonwebtoken.Claims;
import org.mockapp.security.exception.JwtInvalidAuthenticationTokenException;
import org.mockapp.security.exception.UnauthorizedException;
import org.mockapp.security.model.JwtAuthenticationToken;
import org.mockapp.security.model.JwtUserDetails;
import org.mockapp.service.session.SessionService;
import org.mockapp.service.user.UserService;
import org.mockapp.util.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  @Autowired
  private UserService userService;

  @Autowired
  private SessionService sessionService;

  @Override
  protected void additionalAuthenticationChecks(
      UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
  }

  @Override
  protected UserDetails retrieveUser(
      String username, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
    String jwtToken = jwtAuthenticationToken.getToken();
    Claims claims = this.userService.parseJwtToken(jwtToken);
    if (claims == null) {
      throw new JwtInvalidAuthenticationTokenException("Invalid JWT token");
    }
    if (StringUtils.isEmpty(claims.getSubject())) {
      throw new JwtInvalidAuthenticationTokenException("Invalid JWT token");
    }
    Credential.setUsername(claims.getSubject());
    Credential.setSessionId(String.valueOf(claims.get("sessionId")));
    boolean isAuthorized = false;
    try {
      isAuthorized = this.sessionService.isAuthorized();
    } catch (Exception e) {
      throw new UnauthorizedException(e.getMessage());
    }
    if (!isAuthorized) {
      throw new UnauthorizedException("Unauthorized api access");
    }
    return new JwtUserDetails(claims.getSubject(), jwtToken);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return JwtAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
