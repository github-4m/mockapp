package id.co.blogspot.fathan.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
@Entity
@Table(
    name = User.TABLE_NAME,
    uniqueConstraints = {@UniqueConstraint(columnNames = {User.COLUMN_USERNAME})}
)
public class User implements Serializable {

  public static final String TABLE_NAME = "JWT_USER";
  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_MARK_FOR_DELETE = "MARK_FOR_DELETE";
  public static final String COLUMN_USERNAME = "USERNAME";
  public static final String COLUMN_PASSWORD = "PASSWORD";
  public static final String COLUMN_NAME = "NAME";
  public static final String COLUMN_EMAIL = "EMAIL";

  @Id
  @Column(name = User.COLUMN_ID)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @org.springframework.data.annotation.Id
  private String id;

  @Column(name = User.COLUMN_MARK_FOR_DELETE)
  private boolean markForDelete = false;

  @Column(name = User.COLUMN_USERNAME, nullable = false)
  private String username;

  @Column(name = User.COLUMN_PASSWORD, nullable = false)
  private String password;

  @Column(name = User.COLUMN_NAME, nullable = false)
  private String name;

  @Column(name = User.COLUMN_EMAIL, nullable = false)
  private String email;

  public User() {
  }

  public User(String id, boolean markForDelete, String username, String password, String name) {
    this.id = id;
    this.markForDelete = markForDelete;
    this.username = username;
    this.password = password;
    this.name = name;
  }

  public User(
      String id,
      boolean markForDelete,
      String username,
      String password,
      String name,
      String email) {
    this(id, markForDelete, username, password, name);
    this.email = email;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "User{"
        + "id='"
        + id
        + '\''
        + ", markForDelete="
        + markForDelete
        + ", username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\''
        + ", name='"
        + name
        + '\''
        + ", email='"
        + email
        + '\''
        + '}';
  }
}
