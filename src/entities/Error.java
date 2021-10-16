package entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="Error")
public class Error {
    @Id
    @GenericGenerator(name="incgenerator" , strategy="increment")
    @GeneratedValue(generator="incgenerator")
    @Column(name="id", unique=true, nullable=false)
    private int id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ref_user", nullable=false)
    private User user;

    @Column(name="date", nullable=false)
    private Timestamp date;

    @Column(name="error", nullable=false)
    @Type(type="text")
    private String error;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
        //this.date = new java.sql.Date(new java.util.Date().getTime());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
}
