package org.mockapp.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 4than.mustaqiim on 3/9/2017.
 */
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = Endpoint.TABLE_NAME)
public class Endpoint implements Serializable {

  public static final String TABLE_NAME = "M_ENDPOINT";
  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_MARK_FOR_DELETE = "MARK_FOR_DELETE";
  public static final String COLUMN_VERSION = "OPTLOCK";
  public static final String COLUMN_CREATED_BY = "CREATED_BY";
  public static final String COLUMN_CREATED_DATE = "CREATED_DATE";
  public static final String COLUMN_UPDATED_BY = "UPDATED_BY";
  public static final String COLUMN_UPDATED_DATE = "UPDATED_DATE";
  public static final String COLUMN_MODULE_CODE = "MODULE_CODE";
  public static final String COLUMN_CODE = "CODE";
  public static final String COLUMN_URL = "URL";
  public static final String COLUMN_REQUEST_METHOD = "REQUEST_METHOD";
  public static final String COLUMN_REQUEST_HEADER = "REQUEST_HEADER";
  public static final String COLUMN_REQUEST_PARAM = "REQUEST_PARAM";
  public static final String COLUMN_PATH_VARIABLE = "PATH_VARIABLE";
  public static final String COLUMN_REQUEST_BODY = "REQUEST_BODY";
  public static final String COLUMN_RESPONSE_HEADER = "RESPONSE_HEADER";
  public static final String COLUMN_RESPONSE_BODY = "RESPONSE_BODY";
  public static final String COLUMN_ENABLE = "ENABLE";
  public static final String COLUMN_MODULE_ID = "MODULE_ID";

  @Id
  @Column(name = Endpoint.COLUMN_ID)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @org.springframework.data.annotation.Id
  private String id;

  @Column(name = Endpoint.COLUMN_MARK_FOR_DELETE)
  private boolean markForDelete = false;

  @Version
  @Column(name = Endpoint.COLUMN_VERSION)
  private Long version = 0L;

  @CreatedBy
  @Column(name = Endpoint.COLUMN_CREATED_BY, nullable = false)
  private String createdBy;

  @CreatedDate
  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = Endpoint.COLUMN_CREATED_DATE, nullable = false)
  private Date createdDate;

  @LastModifiedBy
  @Column(name = Endpoint.COLUMN_UPDATED_BY, nullable = false)
  private String updatedBy;

  @LastModifiedDate
  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = Endpoint.COLUMN_UPDATED_DATE, nullable = false)
  private Date updatedDate;

  @Column(name = Endpoint.COLUMN_MODULE_CODE, nullable = false)
  private String moduleCode;

  @Column(name = Endpoint.COLUMN_CODE, nullable = false)
  private String code;

  @Column(name = Endpoint.COLUMN_URL, nullable = false)
  private byte[] url;

  @Enumerated(value = EnumType.STRING)
  @Column(name = Endpoint.COLUMN_REQUEST_METHOD, nullable = false)
  private RequestMethod requestMethod;

  @Column(name = Endpoint.COLUMN_REQUEST_HEADER)
  private byte[] requestHeader;

  @Column(name = Endpoint.COLUMN_REQUEST_PARAM)
  private byte[] requestParam;

  @Column(name = Endpoint.COLUMN_PATH_VARIABLE)
  private byte[] pathVariable;

  @Column(name = Endpoint.COLUMN_REQUEST_BODY)
  private byte[] requestBody;

  @Column(name = Endpoint.COLUMN_RESPONSE_HEADER)
  private byte[] responseHeader;

  @Column(name = Endpoint.COLUMN_RESPONSE_BODY)
  private byte[] responseBody;

  @Column(name = Endpoint.COLUMN_ENABLE)
  private boolean enable = false;

  @ManyToOne
  @JoinColumn(name = Endpoint.COLUMN_MODULE_ID)
  private Module module;

  public Endpoint() {
  }

  public Endpoint(String id, boolean markForDelete, Long version, String createdBy,
      Date createdDate, String updatedBy, Date updatedDate, String moduleCode, String code,
      byte[] url, RequestMethod requestMethod, byte[] requestHeader, byte[] requestParam,
      byte[] pathVariable, byte[] requestBody, byte[] responseHeader, byte[] responseBody,
      boolean enable, Module module) {
    this.id = id;
    this.markForDelete = markForDelete;
    this.version = version;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.updatedBy = updatedBy;
    this.updatedDate = updatedDate;
    this.moduleCode = moduleCode;
    this.code = code;
    this.url = url;
    this.requestMethod = requestMethod;
    this.requestHeader = requestHeader;
    this.requestParam = requestParam;
    this.pathVariable = pathVariable;
    this.requestBody = requestBody;
    this.responseHeader = responseHeader;
    this.responseBody = responseBody;
    this.enable = enable;
    this.module = module;
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

  public byte[] getUrl() {
    return url;
  }

  public void setUrl(byte[] url) {
    this.url = url;
  }

  public RequestMethod getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(RequestMethod requestMethod) {
    this.requestMethod = requestMethod;
  }

  public byte[] getRequestHeader() {
    return requestHeader;
  }

  public void setRequestHeader(byte[] requestHeader) {
    this.requestHeader = requestHeader;
  }

  public byte[] getRequestParam() {
    return requestParam;
  }

  public void setRequestParam(byte[] requestParam) {
    this.requestParam = requestParam;
  }

  public byte[] getPathVariable() {
    return pathVariable;
  }

  public void setPathVariable(byte[] pathVariable) {
    this.pathVariable = pathVariable;
  }

  public byte[] getRequestBody() {
    return requestBody;
  }

  public void setRequestBody(byte[] requestBody) {
    this.requestBody = requestBody;
  }

  public byte[] getResponseHeader() {
    return responseHeader;
  }

  public void setResponseHeader(byte[] responseHeader) {
    this.responseHeader = responseHeader;
  }

  public byte[] getResponseBody() {
    return responseBody;
  }

  public void setResponseBody(byte[] responseBody) {
    this.responseBody = responseBody;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  @Override
  public String toString() {
    return "Endpoint{" +
        "id='" + id + '\'' +
        ", markForDelete=" + markForDelete +
        ", version=" + version +
        ", createdBy='" + createdBy + '\'' +
        ", createdDate=" + createdDate +
        ", updatedBy='" + updatedBy + '\'' +
        ", updatedDate=" + updatedDate +
        ", moduleCode='" + moduleCode + '\'' +
        ", code='" + code + '\'' +
        ", url=" + Arrays.toString(url) +
        ", requestMethod=" + requestMethod +
        ", requestHeader=" + Arrays.toString(requestHeader) +
        ", requestParam=" + Arrays.toString(requestParam) +
        ", pathVariable=" + Arrays.toString(pathVariable) +
        ", requestBody=" + Arrays.toString(requestBody) +
        ", responseHeader=" + Arrays.toString(responseHeader) +
        ", responseBody=" + Arrays.toString(responseBody) +
        ", enable=" + enable +
        ", module=" + module +
        '}';
  }
}
