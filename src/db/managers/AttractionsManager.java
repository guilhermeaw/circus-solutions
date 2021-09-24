package db.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Attraction;
import utils.ApplicationUtilities;

public class AttractionsManager extends DefaultManager<Attraction> {
  private static AttractionsManager instance;

  private AttractionsManager() {
  }

  public static AttractionsManager getInstance() {
    if (instance == null){
      instance = new AttractionsManager();
    }

    return instance;
  }

  public List<Attraction> getAll() {
    List attractionList = new ArrayList();
    
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      attractionList = session.createQuery("FROM Attraction").list();
      session.getTransaction().commit();
      session.close();

    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
      
    return attractionList;
  }
}
