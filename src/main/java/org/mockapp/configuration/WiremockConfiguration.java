package org.mockapp.configuration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 4than.mustaqiim on 3/9/2017.
 */
@Configuration
public class WiremockConfiguration {

  @Value("${wiremock.host}")
  private String wiremockHost;

  @Value("${wiremock.port}")
  private String wiremockPort;

  @Value("${wiremock.directory}")
  private String wiremockDirectory;

  @Bean
  public WireMockServer wireMockServer() throws Exception {
    WireMockConfiguration wireMockConfiguration = new WireMockConfiguration();
    wireMockConfiguration.bindAddress(this.wiremockHost);
    wireMockConfiguration.port(Integer.parseInt(this.wiremockPort));
    wireMockConfiguration.usingFilesUnderDirectory(this.wiremockDirectory);
    WireMockServer wireMockServer = new WireMockServer(wireMockConfiguration);
    wireMockServer.start();
    return wireMockServer;
  }

}
