package entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="login")
public class Login {
    @Id
    @GenericGenerator(name="incgenerator" , strategy="increment")
    @GeneratedValue(generator="incgenerator")
    @Column(name="id", unique=true, nullable=false)
    private int id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ref_user", nullable=false)
    private User usuarioLogado;

    @Column(name="date", nullable=false)
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        //this.date = new java.sql.Date(new java.util.Date().getTime());
    }

    public User getUserLogged() {
        return usuarioLogado;
    }

    public void setUserLogged(User usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
