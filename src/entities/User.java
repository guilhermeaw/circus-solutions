package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="id")
  private int id;
  
  @NaturalId
  private String login;

  private String name;

  @Column
  private String password;

  public int getId() {
      return id;
  }

  public void setId(int id) {
      this.id = id;
  }

  public String getLogin() {
      return login;
  }

  public void setName(String name) {
      this.name = name;
  }
  
  public String getName() {
      return name;
  }

  public void setLogin(String login) {
      this.login = login;
  }

  public String getPassword() {
      return password;
  }

  public void setPassword(String password) {
      this.password = password;
  }

  @Override
  public String toString() {
      return name;
  }
}
