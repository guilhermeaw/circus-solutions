package filters.data;

import java.sql.Date;

import entities.City;
import entities.User;

public class ShowFilter {
  private City city = null;
  private User author = null;
  private Date startDate = null;
  private Date endDate = null;

  public ShowFilter() {}

  public City getCity() {
      return city;
  }

  public void setCity(City city) {
      this.city = city;
  }

  public User getAuthor() {
      return author;
  }

  public void setAuthor(User author) {
      this.author = author;
  }

  public Date getStartDate() {
      return startDate;
  }

  public void setStartDate(Date startDate) {
      this.startDate = startDate;
  }

  public Date getEndDate() {
      return endDate;
  }

  public void setEndDate(Date endDate) {
      this.endDate = endDate;
  }
}
