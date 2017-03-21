package org.mockapp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by 4than.mustaqiim on 3/9/2017.
 */
@Entity
@Table(name = Module.TABLE_NAME)
public class Module extends BaseEntity {

  public static final String TABLE_NAME = "M_MODULE";
  public static final String COLUMN_CODE = "CODE";
  public static final String COLUMN_NAME = "NAME";
  public static final String COLUMN_CONTEXT_PATH = "CONTEXT_PATH";
  public static final String COLUMN_ENABLE = "ENABLE";

  @Column(name = Module.COLUMN_CODE, nullable = false)
  private String code;

  @Column(name = Module.COLUMN_NAME, nullable = false)
  private String name;

  @Column(name = Module.COLUMN_CONTEXT_PATH, nullable = false)
  private String contextPath;

  @Column(name = Module.COLUMN_ENABLE)
  private boolean enable = false;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "module", fetch = FetchType.LAZY)
  private List<Endpoint> endpoints = new ArrayList<>();

  public Module() {
  }

  public Module(String id, boolean markForDelete, Long version, String createdBy,
      Date createdDate, String updatedBy, Date updatedDate, String code, String name,
      String contextPath, boolean enable, List<Endpoint> endpoints) {
    super(id, markForDelete, version, createdBy, createdDate, updatedBy, updatedDate);
    this.code = code;
    this.name = name;
    this.contextPath = contextPath;
    this.enable = enable;
    this.endpoints = endpoints;
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

  public List<Endpoint> getEndpoints() {
    return endpoints;
  }

  public void setEndpoints(List<Endpoint> endpoints) {
    this.endpoints = endpoints;
  }

  @Override
  public String toString() {
    return "Module{" +
        "code='" + code + '\'' +
        ", name='" + name + '\'' +
        ", contextPath='" + contextPath + '\'' +
        ", enable=" + enable +
        ", endpoints=" + endpoints +
        '}';
  }
}
