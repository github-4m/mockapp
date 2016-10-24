package id.co.blogspot.fathan.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
@Entity
@Table(name = User.TABLE_NAME, uniqueConstraints = {@UniqueConstraint(columnNames = {User.COLUMN_USERNAME})})
public class User implements Serializable {

  public static final String TABLE_NAME = "JWT_USER";
  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_MARK_FOR_DELETE = "MARK_FOR_DELETE";
  public static final String COLUMN_USERNAME = "USERNAME";
  public static final String COLUMN_PASSWORD = "PASSWORD";

  @Id
  @Column(name = User.COLUMN_ID)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @org.springframework.data.annotation.Id
  private String id;

  @Column(name = User.COLUMN_MARK_FOR_DELETE)
  private boolean markForDelete = false;

  @Column(name = User.COLUMN_USERNAME)
  private String username;

  @Column(name = User.COLUMN_PASSWORD)
  private String password;

  public User() {
  }

  public User(String id, boolean markForDelete, String username, String password) {
    this.id = id;
    this.markForDelete = markForDelete;
    this.username = username;
    this.password = password;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
            "id='" + id + '\'' +
            ", markForDelete=" + markForDelete +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            '}';
  }

}
