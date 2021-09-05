package db.managers;

import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Occupations;

public class OccupationsManager implements IDefaultManager<Occupations> {
  @Override
  public void create(Occupations value) throws Exception {
    Database db = Database.getInstance();
    Session session = db.openSession();

    session.beginTransaction();
    session.save(value);
    session.getTransaction().commit();
    session.close();    
  }

  @Override
  public void update(Occupations value) throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void delete(Occupations value) throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Occupations getById(int id) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Occupations> getAll() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
  
}
