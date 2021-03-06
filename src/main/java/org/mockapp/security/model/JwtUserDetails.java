package org.mockapp.security.model;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
public class JwtUserDetails implements UserDetails {

  private String username;
  private String token;

  public JwtUserDetails() {
  }

  public JwtUserDetails(String username, String token) {
    this.username = username;
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
