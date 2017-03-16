package org.mockapp.dto.module;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 * Created by fathan.mustaqiim on 3/15/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateModuleRequest implements Serializable {

  private String name;
  private String contextPath;

  public CreateModuleRequest() {
  }

  public CreateModuleRequest(String name, String contextPath) {
    this.name = name;
    this.contextPath = contextPath;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContextPath() {
    return contextPath;
  }

  public void setContextPath(String contextPath) {
    this.contextPath = contextPath;
  }

  @Override
  public String toString() {
    return "CreateModuleRequest{" +
        "name='" + name + '\'' +
        ", contextPath='" + contextPath + '\'' +
        '}';
  }
}
