package entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="artists")
public class Artist {
  @Id
  @GenericGenerator(name="incgenerator" , strategy="increment")
  @GeneratedValue(generator="incgenerator")
  @Column(name="id", unique=true, nullable=false)
  private int id;
  
  @Column(name="name", nullable=false, length=200)
  private String name;

  @Column(name="cpf", unique=true, nullable=false, length=11)
  private String cpf;

  @Column(name="phone", nullable=false, length=11)
  private String phone;

  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="ref_occupation", nullable=false)
  private Occupation occupation;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
      return this.name;
    }
}
