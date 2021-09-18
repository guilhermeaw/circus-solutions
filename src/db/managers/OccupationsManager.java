package db.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Occupation;
import utils.ApplicationUtilities;

public class OccupationsManager extends DefaultManager<Occupation> {
  private static OccupationsManager instance;

  private OccupationsManager() {
  }

  public static OccupationsManager getInstance() {
    if (instance == null){
      instance = new OccupationsManager();
    }

    return instance;
  }

  public List<Occupation> getAll() {
    List occupationList = new ArrayList();
    
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      occupationList = session.createQuery("FROM Occupation").list();
      session.getTransaction().commit();
      session.close();

    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
      
    return occupationList;
  }
}
