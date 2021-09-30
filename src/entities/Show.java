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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import formatters.DateFormatter;

@Entity
@Table(name="shows")
public class Show {
    @Id
    @GenericGenerator(name="incgenerator" , strategy="increment")
    @GeneratedValue(generator="incgenerator")
    @Column(name="id", unique=true, nullable=false)
    private int id;

    @Column(name="capacity", nullable=false)
    private int capacity;
    
    @Column(name="ref_city", nullable=false)
    private Long cityId;

    @Column(name="date", nullable=false)
    private Date date;

    @Transient
    private City city;

    @Type(type="true_false")
    @Column(name="active", nullable=false)
    private boolean isShowActive = false;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ref_user", nullable=false)
    private User author;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
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

    public boolean isIsShowActive() {
        return isShowActive;
    }

    public void setIsShowActive(boolean isShowActive) {
        this.isShowActive = isShowActive;
    }

    @Override
    public String toString() {
        return city.getName() + " - " + DateFormatter.format(date);
    }
}
