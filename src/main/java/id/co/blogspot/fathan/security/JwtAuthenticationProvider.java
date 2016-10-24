package id.co.blogspot.fathan.security;

import id.co.blogspot.fathan.entity.User;
import id.co.blogspot.fathan.security.exception.JwtInvalidAuthenticationTokenException;
import id.co.blogspot.fathan.security.model.JwtAuthenticationToken;
import id.co.blogspot.fathan.security.model.JwtUserDetails;
import id.co.blogspot.fathan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  @Autowired
  private UserService userService;

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
    User user = this.userService.parseJwtToken(jwtToken);
    if (user == null) {
      throw new JwtInvalidAuthenticationTokenException("Invalid JWT token");
    }
    return new JwtUserDetails(user.getUsername(), jwtToken);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return JwtAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
