package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="users")
public class User {
  @Id
  @GenericGenerator(name="incgenerator" , strategy="increment")
  @GeneratedValue(generator="incgenerator")
  @Column(name="id", unique=true, nullable=false)
  private int id;
  
  @NaturalId
  @Column(name="login", unique=true, nullable=false, length=30)
  private String login;

  @Column(name="name", nullable=false, length=100)
  private String name;

  @Column(name="password", nullable=false, length=100)
  private String password;

  @Column(name="role", nullable=false, length=8)
  @Enumerated(EnumType.STRING)
  private Role role;

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

    public Role getRole() {
      return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return name;
    }
}
