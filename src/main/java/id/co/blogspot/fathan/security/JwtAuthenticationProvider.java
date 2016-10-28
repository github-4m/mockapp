package id.co.blogspot.fathan.security;

import id.co.blogspot.fathan.security.exception.JwtInvalidAuthenticationTokenException;
import id.co.blogspot.fathan.security.exception.UnauthorizedException;
import id.co.blogspot.fathan.security.model.JwtAuthenticationToken;
import id.co.blogspot.fathan.security.model.JwtUserDetails;
import id.co.blogspot.fathan.service.session.SessionService;
import id.co.blogspot.fathan.service.user.UserService;
import id.co.blogspot.fathan.util.Credential;
import io.jsonwebtoken.Claims;
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
  protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                UsernamePasswordAuthenticationToken authentication)
          throws AuthenticationException {
  }

  @Override
  protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
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
