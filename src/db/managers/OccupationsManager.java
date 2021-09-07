package db.managers;

import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Occupation;

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
    // TODO Auto-generated method stub
    return null;
  }
  
}
