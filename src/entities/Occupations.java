package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="occupations")
public class Occupations {
  @Id
  @Column(name="id")
  private int id;
  
  @Column(name="name")
  private String name;

  @Column(name="description")
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
