package org.mockapp.dto.endpoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by fathan.mustaqiim on 3/15/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateEndpointRequest implements Serializable {

  private String moduleCode;
  private String url;
  private RequestMethod requestMethod;
  private Set<String> requestParams = new HashSet<>();
  private Set<String> pathVariables = new HashSet<>();
  private String responseBody;

  public CreateEndpointRequest() {
  }

  public CreateEndpointRequest(String moduleCode, String url,
      RequestMethod requestMethod, Set<String> requestParams,
      Set<String> pathVariables, String responseBody) {
    this.moduleCode = moduleCode;
    this.url = url;
    this.requestMethod = requestMethod;
    this.requestParams = requestParams;
    this.pathVariables = pathVariables;
    this.responseBody = responseBody;
  }

  public String getModuleCode() {
    return moduleCode;
  }

  public void setModuleCode(String moduleCode) {
    this.moduleCode = moduleCode;
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
    return "CreateEndpointRequest{" +
        "moduleCode='" + moduleCode + '\'' +
        ", url='" + url + '\'' +
        ", requestMethod=" + requestMethod +
        ", requestParams=" + requestParams +
        ", pathVariables=" + pathVariables +
        ", responseBody='" + responseBody + '\'' +
        '}';
  }
}
