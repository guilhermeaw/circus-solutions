package entities;

import java.time.LocalTime;

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
@Table(name="schedules")
public class Schedule {
  @Id
  @GenericGenerator(name="incgenerator" , strategy="increment")
  @GeneratedValue(generator="incgenerator")
  @Column(name="id", unique=true, nullable=false)
  private int id;

  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="ref_show")
  private Show show;

  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="ref_attraction")
  private Attraction attraction;

  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="ref_artist")
  private Artist artist;

  @Column(name="start_time")
  private LocalTime startTime;
  
  @Column(name="end_time")
  private LocalTime endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
