package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="occupation")
public class Occupation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="id", unique=true, nullable=false)
  private int id;
  
  @Column(name="name", nullable=false, length=200)
  private String name;

  @Column(name="description", nullable=false, length=500)
  private String description;

  public int getId() {
      return id;
  }

  public void setId(int id) {
      this.id = id;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
      this.description = description;
  }
}
