package db.managers;

import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Occupation;

public class OccupationsManager extends DefaultManager<Occupation> {
  private static OccupationsManager instance;

  private void OccupationsManager() {
  }

  public static OccupationsManager getInstance() {
    if (instance == null){
      instance = new OccupationsManager();
    }

    return instance;
  }

  public List<Occupation> getAll() {
    // TODO Auto-generated method stub
    return null;
  }
  
}
