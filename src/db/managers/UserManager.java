package db.managers;

import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.User;
import utils.ApplicationUtilities;

public class UserManager implements IDefaultManager<User> {
  private static UserManager instance;
  private Database db = Database.getInstance();

  private void UserManager() {
  }

  public static UserManager getInstance() {
    if (instance == null){
      instance = new UserManager();
    }

    return instance;
  }

  @Override
  public void create(User value) throws Exception {
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

  public User getByLoginAndPassword(String login, String password) {
    User user = null;
    
    Session session = db.openSession();
    session.beginTransaction();

    try {
      user = session.createQuery("from User u where u.login = :login and u.password = :password", User.class)
        .setParameter("login", login)
        .setParameter("password", password)
        .getSingleResult();

    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }

    session.getTransaction().commit();
    session.close(); 

    return user;
  }

  @Override
  public List<User> getAll() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
  
}
