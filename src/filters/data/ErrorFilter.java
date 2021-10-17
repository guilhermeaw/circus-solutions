package filters.data;

import java.sql.Date;

import entities.User;

public class ErrorFilter {
  private User user = null;
  private Date startDate = null;
  private Date endDate = null;

  public ErrorFilter() {}

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
