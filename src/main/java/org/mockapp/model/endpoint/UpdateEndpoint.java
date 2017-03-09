package org.mockapp.model.endpoint;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 4than.mustaqiim on 3/9/2017.
 */
public class UpdateEndpoint implements Serializable {

  private String code;
  private String url;
  private RequestMethod requestMethod;
  private Set<String> requestParams = new HashSet<>();
  private Set<String> pathVariables = new HashSet<>();
  private String responseBody;

  public UpdateEndpoint() {
  }

  public UpdateEndpoint(String code, String url,
      RequestMethod requestMethod, Set<String> requestParams,
      Set<String> pathVariables, String responseBody) {
    this.code = code;
    this.url = url;
    this.requestMethod = requestMethod;
    this.requestParams = requestParams;
    this.pathVariables = pathVariables;
    this.responseBody = responseBody;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public RequestMethod getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(RequestMethod requestMethod) {
    this.requestMethod = requestMethod;
  }

  public Set<String> getRequestParams() {
    return requestParams;
  }

  public void setRequestParams(Set<String> requestParams) {
    this.requestParams = requestParams;
  }

  public Set<String> getPathVariables() {
    return pathVariables;
  }

  public void setPathVariables(Set<String> pathVariables) {
    this.pathVariables = pathVariables;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public void setResponseBody(String responseBody) {
    this.responseBody = responseBody;
  }

  @Override
  public String toString() {
    return "UpdateEndpoint{" +
        "code='" + code + '\'' +
        ", url='" + url + '\'' +
        ", requestMethod=" + requestMethod +
        ", requestParams=" + requestParams +
        ", pathVariables=" + pathVariables +
        ", responseBody='" + responseBody + '\'' +
        '}';
  }
}
