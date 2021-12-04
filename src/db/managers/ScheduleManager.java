package db.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Schedule;
import entities.Show;

public class ScheduleManager extends DefaultManager<Schedule> {
  private static ScheduleManager instance;

  public static ScheduleManager getInstance() {
    if (instance == null){
      instance = new ScheduleManager();
    }

    return instance;
  }
  
  private ScheduleManager() {
  }

  public List<Schedule> getSchedulesByShow(Show show) {
    List<Schedule> schedules = new ArrayList<Schedule>();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      schedules = session.createQuery("select sc FROM Schedule sc inner join sc.show sh where sh.id = :showId", Schedule.class)
        .setParameter("showId", show.getId())
        .list();
      
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      handleException(e);
    }

    return schedules;
  }
}
