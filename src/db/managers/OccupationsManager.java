package db.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Occupation;
import utils.ApplicationUtilities;

public class OccupationsManager implements IDefaultManager<Occupation> {
  private static OccupationsManager instance;

  private void OccupationsManager() {
  }

  public static OccupationsManager getInstance() {
    if (instance == null){
      instance = new OccupationsManager();
    }

    return instance;
  }

  @Override
  public void create(Occupation value) throws Exception {
    Database db = Database.getInstance();
    Session session = db.openSession();

    session.beginTransaction();
    session.save(value);
    session.getTransaction().commit();
    session.close();    
  }

  @Override
  public void update(Occupation value) throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void delete(Occupation value) throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Occupation getById(int id) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Occupation> getAll() throws Exception {
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
