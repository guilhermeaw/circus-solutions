package entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import db.managers.CityManager;

@Entity
@Table(name="shows")
public class Show {
    @Id
    @GenericGenerator(name="incgenerator" , strategy="increment")
    @GeneratedValue(generator="incgenerator")
    @Column(name="id", unique=true, nullable=false)
    private int id;

    @Column(name="capacity", unique=true, nullable=false)
    private int capacity;
    
    @Column(name="cityId", unique=true, nullable=false)
    private int cityId;

    @Column(name="date", unique=true, nullable=false)
    private Date date;

    // @Column(name="city", unique=true, nullable=false)
    // private City city;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ref_users", nullable=false)
    private User author;

    // public City getCity() {
    //     return city;
    // }

    // public void setCity(City city) {
    //     this.city = city;
    // }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
