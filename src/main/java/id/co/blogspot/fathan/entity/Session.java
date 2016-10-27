package id.co.blogspot.fathan.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by fathan.mustaqiim on 10/27/2016.
 */
@Entity
@Table(name = Session.TABLE_NAME, uniqueConstraints = {@UniqueConstraint(columnNames = {Session.COLUMN_USERNAME})})
public class Session implements Serializable {

  public static final String TABLE_NAME = "JWT_SESSION";
  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_USERNAME = "USERNAME";
  public static final String COLUMN_SESSION_ID = "SESSION_ID";
  public static final String COLUMN_HOSTNAME = "HOSTNAME";

  @Id
  @Column(name = Session.COLUMN_ID)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @org.springframework.data.annotation.Id
  private String id;

  @Column(name = Session.COLUMN_USERNAME, nullable = false)
  private String username;

  @Column(name = Session.COLUMN_SESSION_ID, nullable = false)
  private String sessionId;

  @Column(name = Session.COLUMN_HOSTNAME, nullable = false)
  private String hostname;

  public Session() {
  }

  public Session(String id, String username, String sessionId, String hostname) {
    this.id = id;
    this.username = username;
    this.sessionId = sessionId;
    this.hostname = hostname;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  @Override
  public String toString() {
    return "Session{" +
            "id='" + id + '\'' +
            ", username='" + username + '\'' +
            ", sessionId='" + sessionId + '\'' +
            ", hostname='" + hostname + '\'' +
            '}';
  }

}
