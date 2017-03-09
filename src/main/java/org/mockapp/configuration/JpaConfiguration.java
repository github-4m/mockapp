package org.mockapp.configuration;

import org.mockapp.security.model.JwtAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by fathan.mustaqiim on 1/18/2017.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfiguration {

  @Bean
  public JwtAuditorAware auditorAware() throws Exception {
    return new JwtAuditorAware();
  }
}
