package org.mockapp.outbound.wiremock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mockapp.outbound.HttpInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Created by fathan.mustaqiim on 3/12/2017.
 */
@Component
public class WiremockOutboundBean implements WiremockOutbound {

  @Autowired
  private HttpInvoker httpInvoker;

  @Value("${wiremock.scheme}")
  private String wiremockScheme;

  @Value("${wiremock.host}")
  private String wiremockHost;

  @Value("${wiremock.port}")
  private String wiremockPort;

  @Override
  public void reset() throws Exception {
    this.httpInvoker.invoke(HttpMethod.POST, this.wiremockScheme, this.wiremockHost,
        Integer.parseInt(this.wiremockPort), WiremockOutboundPath.RESET, null);
  }

  @Override
  public String mockup(HttpMethod method, HttpServletRequest request, HttpServletResponse response,
      String path, String requestBody) throws Exception {
    return this.httpInvoker
        .invoke(method, request, response, this.wiremockScheme, this.wiremockHost,
            Integer.parseInt(this.wiremockPort), path, requestBody);
  }
}
