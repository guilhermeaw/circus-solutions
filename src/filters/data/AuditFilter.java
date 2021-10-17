package filters.data;

import java.sql.Date;

import entities.User;

public class AuditFilter {
  private String type = null;
  private User user = null;
  private Date startDate = null;
  private Date endDate = null;

  public AuditFilter() {}

  public String getType() {
      return type;
  }

  public void setType(String type) {
      this.type = type;
  }

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
