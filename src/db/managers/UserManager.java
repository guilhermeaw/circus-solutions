package db.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.User;
import utils.ApplicationUtilities;

public class UserManager extends DefaultManager<User> {
  private static UserManager instance;
  private Database db = Database.getInstance();

  private UserManager() {
  }

  public static UserManager getInstance() {
    if (instance == null){
      instance = new UserManager();
    }

    return instance;
  }

  public User getByLoginAndPassword(String login, String password) {
    User user = null;
    
    try {
      Session session = db.openSession();
      session.beginTransaction();

      user = session.createQuery("from User u where u.login = :login and u.password = :password", User.class)
        .setParameter("login", login)
        .setParameter("password", password)
        .uniqueResult();
        
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      handleException(e);
    }
        
    return user;
  }

  public List<User> getAllExcludingCurrentUser() {
    List<User> userList = new ArrayList<User>();
    User activeUser = ApplicationUtilities.getInstance().getActiveUser();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      userList = session.createQuery("FROM User u where u.id <> :id", User.class)
        .setParameter("id", activeUser.getId())
        .list();
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      handleException(e);
    }
      
    return userList;
  }

  public List<User> getAll() {
    List<User> userList = new ArrayList<User>();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      userList = session.createQuery("FROM User", User.class).list();
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      handleException(e);
    }
      
    return userList;
  }
}
