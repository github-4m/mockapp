package org.mockapp.model.wiremock;

import java.io.Serializable;

/**
 * Created by fathan.mustaqiim on 3/15/2017.
 */
public class Wiremock implements Serializable {

  private WiremockRequest request;
  private WiremockResponse response;

  public Wiremock() {
  }

  public Wiremock(WiremockRequest request, WiremockResponse response) {
    this.request = request;
    this.response = response;
  }

  public WiremockRequest getRequest() {
    return request;
  }

  public void setRequest(WiremockRequest request) {
    this.request = request;
  }

  public WiremockResponse getResponse() {
    return response;
  }

  public void setResponse(WiremockResponse response) {
    this.response = response;
  }

  @Override
  public String toString() {
    return "Wiremock{" +
        "request=" + request +
        ", response=" + response +
        '}';
  }
}
