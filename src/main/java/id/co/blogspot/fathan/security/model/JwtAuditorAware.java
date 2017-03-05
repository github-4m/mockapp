package id.co.blogspot.fathan.security.model;

import id.co.blogspot.fathan.util.Constant;
import id.co.blogspot.fathan.util.Credential;
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
