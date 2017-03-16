package org.mockapp.dto.module;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fathan.mustaqiim on 3/15/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModuleResponse implements Serializable {

  private String createdBy;
  private Date createdDate;
  private String updatedBy;
  private Date updatedDate;
  private String code;
  private String name;
  private String contextPath;
  private boolean enable = false;

  public ModuleResponse() {
  }

  public ModuleResponse(String createdBy, Date createdDate, String updatedBy,
      Date updatedDate, String code, String name, String contextPath, boolean enable) {
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.updatedBy = updatedBy;
    this.updatedDate = updatedDate;
    this.code = code;
    this.name = name;
    this.contextPath = contextPath;
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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  @Override
  public String toString() {
    return "ModuleResponse{" +
        "createdBy='" + createdBy + '\'' +
        ", createdDate=" + createdDate +
        ", updatedBy='" + updatedBy + '\'' +
        ", updatedDate=" + updatedDate +
        ", code='" + code + '\'' +
        ", name='" + name + '\'' +
        ", contextPath='" + contextPath + '\'' +
        ", enable=" + enable +
        '}';
  }
}
