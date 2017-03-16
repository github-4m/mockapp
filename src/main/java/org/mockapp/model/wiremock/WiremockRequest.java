package org.mockapp.model.wiremock;

import java.io.Serializable;

/**
 * Created by fathan.mustaqiim on 3/15/2017.
 */
public class WiremockRequest implements Serializable {

  private String method;
  private String urlPattern;

  public WiremockRequest() {
  }

  public WiremockRequest(String method, String urlPattern) {
    this.method = method;
    this.urlPattern = urlPattern;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getUrlPattern() {
    return urlPattern;
  }

  public void setUrlPattern(String urlPattern) {
    this.urlPattern = urlPattern;
  }

  @Override
  public String toString() {
    return "WiremockRequest{" +
        "method='" + method + '\'' +
        ", urlPattern='" + urlPattern + '\'' +
        '}';
  }
}
