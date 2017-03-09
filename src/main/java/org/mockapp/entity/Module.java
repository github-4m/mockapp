package org.mockapp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Created by 4than.mustaqiim on 3/9/2017.
 */
@Entity
@Table(name = Module.TABLE_NAME)
public class Module implements Serializable {

  public static final String TABLE_NAME = "M_MODULE";
  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_MARK_FOR_DELETE = "MARK_FOR_DELETE";
  public static final String COLUMN_VERSION = "OPTLOCK";
  public static final String COLUMN_CREATED_BY = "CREATED_BY";
  public static final String COLUMN_CREATED_DATE = "CREATED_DATE";
  public static final String COLUMN_UPDATED_BY = "UPDATED_BY";
  public static final String COLUMN_UPDATED_DATE = "UPDATED_DATE";
  public static final String COLUMN_CODE = "CODE";
  public static final String COLUMN_NAME = "NAME";
  public static final String COLUMN_CONTEXT_PATH = "CONTEXT_PATH";
  public static final String COLUMN_ENABLE = "ENABLE";

  @Id
  @Column(name = Module.COLUMN_ID)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @org.springframework.data.annotation.Id
  private String id;

  @Column(name = Module.COLUMN_MARK_FOR_DELETE)
  private boolean markForDelete = false;

  @Version
  @Column(name = Module.COLUMN_VERSION)
  private Long version = 0L;

  @CreatedBy
  @Column(name = Module.COLUMN_CREATED_BY, nullable = false)
  private String createdBy;

  @CreatedDate
  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = Module.COLUMN_CREATED_DATE, nullable = false)
  private Date createdDate;

  @LastModifiedBy
  @Column(name = Module.COLUMN_UPDATED_BY, nullable = false)
  private String updatedBy;

  @LastModifiedDate
  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = Module.COLUMN_UPDATED_DATE, nullable = false)
  private Date updatedDate;

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
    this.id = id;
    this.markForDelete = markForDelete;
    this.version = version;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.updatedBy = updatedBy;
    this.updatedDate = updatedDate;
    this.code = code;
    this.name = name;
    this.contextPath = contextPath;
    this.enable = enable;
    this.endpoints = endpoints;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isMarkForDelete() {
    return markForDelete;
  }

  public void setMarkForDelete(boolean markForDelete) {
    this.markForDelete = markForDelete;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
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

  public List<Endpoint> getEndpoints() {
    return endpoints;
  }

  public void setEndpoints(List<Endpoint> endpoints) {
    this.endpoints = endpoints;
  }

  @Override
  public String toString() {
    return "Module{" +
        "id='" + id + '\'' +
        ", markForDelete=" + markForDelete +
        ", version=" + version +
        ", createdBy='" + createdBy + '\'' +
        ", createdDate=" + createdDate +
        ", updatedBy='" + updatedBy + '\'' +
        ", updatedDate=" + updatedDate +
        ", code='" + code + '\'' +
        ", name='" + name + '\'' +
        ", contextPath='" + contextPath + '\'' +
        ", enable=" + enable +
        ", endpoints=" + endpoints +
        '}';
  }
}
