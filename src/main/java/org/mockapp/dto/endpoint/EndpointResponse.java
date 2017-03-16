package org.mockapp.dto.endpoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by fathan.mustaqiim on 3/15/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EndpointResponse implements Serializable {

  private String createdBy;
  private Date createdDate;
  private String updatedBy;
  private Date updatedDate;
  private String moduleCode;
  private String code;
  private String url;
  private RequestMethod requestMethod;
  private Set<String> requestParams = new HashSet<>();
  private Set<String> pathVariables = new HashSet<>();
  private String responseBody;
  private boolean enable = false;

  public EndpointResponse() {
  }

  public EndpointResponse(String createdBy, Date createdDate, String updatedBy,
      Date updatedDate, String moduleCode, String code, String url,
      RequestMethod requestMethod, Set<String> requestParams,
      Set<String> pathVariables, String responseBody, boolean enable) {
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.updatedBy = updatedBy;
    this.updatedDate = updatedDate;
    this.moduleCode = moduleCode;
    this.code = code;
    this.url = url;
    this.requestMethod = requestMethod;
    this.requestParams = requestParams;
    this.pathVariables = pathVariables;
    this.responseBody = responseBody;
    this.enable = enable;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getModuleCode() {
    return moduleCode;
  }

  public void setModuleCode(String moduleCode) {
    this.moduleCode = moduleCode;
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

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  @Override
  public String toString() {
    return "EndpointResponse{" +
        "createdBy='" + createdBy + '\'' +
        ", createdDate=" + createdDate +
        ", updatedBy='" + updatedBy + '\'' +
        ", updatedDate=" + updatedDate +
        ", moduleCode='" + moduleCode + '\'' +
        ", code='" + code + '\'' +
        ", url='" + url + '\'' +
        ", requestMethod=" + requestMethod +
        ", requestParams=" + requestParams +
        ", pathVariables=" + pathVariables +
        ", responseBody='" + responseBody + '\'' +
        ", enable=" + enable +
        '}';
  }
}
