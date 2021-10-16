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

@Entity
@Table(name="audit")
public class Audit {
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

    @Column(name="type", nullable=false)
    private String type;

    @Column(name="table_name", nullable=false)
    private String tableName;

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
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
