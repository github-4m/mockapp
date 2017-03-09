package org.mockapp.security.model;

import org.mockapp.util.Constant;
import org.mockapp.util.Credential;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

/**
 * Created by fathan.mustaqiim on 1/18/2017.
 */
public class JwtAuditorAware implements AuditorAware<String> {

  @Override
  public String getCurrentAuditor() {
    return StringUtils.isEmpty(Credential.getUsername())
        ? Constant.INITIATOR
        : Credential.getUsername();
  }
}
