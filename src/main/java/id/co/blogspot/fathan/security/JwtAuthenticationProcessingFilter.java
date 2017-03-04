package id.co.blogspot.fathan.security;

import id.co.blogspot.fathan.security.exception.JwtInvalidAuthenticationTokenException;
import id.co.blogspot.fathan.security.model.JwtAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Created by fathan.mustaqiim on 10/24/2016. */
public class JwtAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

  public JwtAuthenticationProcessingFilter() {
    super("/**");
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    String jwtToken = request.getHeader("Authorization");
    if (StringUtils.isEmpty(jwtToken)) {
      throw new JwtInvalidAuthenticationTokenException("Invalid JWT token");
    }
    JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwtToken);
    return getAuthenticationManager().authenticate(jwtAuthenticationToken);
  }

  @Override
  protected boolean requiresAuthentication(
      HttpServletRequest request, HttpServletResponse response) {
    return true;
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
    chain.doFilter(request, response);
  }
}
