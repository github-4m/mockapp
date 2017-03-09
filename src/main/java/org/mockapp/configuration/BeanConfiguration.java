package org.mockapp.configuration;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockapp.outbound.HttpInvoker;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 4than.mustaqiim on 3/9/2017.
 */
@Configuration
public class BeanConfiguration {

  @Bean
  public ObjectMapper objectMapper() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
    return objectMapper;
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {
    return builder.build();
  }

  @Bean
  public HttpInvoker httpInvoker() throws Exception {
    HttpInvoker httpInvoker = new HttpInvoker();
    return httpInvoker;
  }

}
