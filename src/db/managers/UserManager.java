package db.managers;

import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.User;

public class UserManager implements IDefaultManager<User> {

  @Override
  public void create(User value) throws Exception {
    Database db = Database.getInstance();
    Session session = db.openSession();

    session.beginTransaction();
    session.save(value);
    session.getTransaction().commit();
    session.close();    
  }

  @Override
  public void update(User value) throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void delete(User value) throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public User getById(int id) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> getAll() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
  
}
