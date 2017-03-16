package org.mockapp.model.wiremock;

import java.io.Serializable;

/**
 * Created by fathan.mustaqiim on 3/15/2017.
 */
public class WiremockResponseBody implements Serializable {

  private String endpointCode;
  private String body;

  public WiremockResponseBody() {
  }

  public WiremockResponseBody(String endpointCode, String body) {
    this.endpointCode = endpointCode;
    this.body = body;
  }

  public String getEndpointCode() {
    return endpointCode;
  }

  public void setEndpointCode(String endpointCode) {
    this.endpointCode = endpointCode;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "WiremockResponseBody{" +
        "endpointCode='" + endpointCode + '\'' +
        ", body='" + body + '\'' +
        '}';
  }
}
