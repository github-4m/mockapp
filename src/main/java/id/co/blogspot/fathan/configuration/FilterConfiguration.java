package id.co.blogspot.fathan.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by fathan.mustaqiim on 10/27/2016.
 */
@Configuration
public class FilterConfiguration {

  @Bean
  public FilterRegistrationBean corsFilter() {
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/api/**", corsConfiguration);
    CorsFilter corsFilter = new CorsFilter(urlBasedCorsConfigurationSource);
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(corsFilter);
    filterRegistrationBean.setOrder(0);
    return filterRegistrationBean;
  }

}
