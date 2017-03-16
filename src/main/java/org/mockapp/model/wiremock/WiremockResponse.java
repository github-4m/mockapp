package org.mockapp.model.wiremock;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fathan.mustaqiim on 3/15/2017.
 */
public class WiremockResponse implements Serializable {

  private Integer status;
  private Map<String, String> headers = new HashMap<>();
  private String body;

  public WiremockResponse() {
  }

  public WiremockResponse(Integer status, Map<String, String> headers, String body) {
    this.status = status;
    this.headers = headers;
    this.body = body;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "WiremockResponse{" +
        "status=" + status +
        ", headers=" + headers +
        ", body='" + body + '\'' +
        '}';
  }
}
