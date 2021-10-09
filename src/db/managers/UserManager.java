package db.managers;

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
      ApplicationUtilities.getInstance().handleException(e);
    }
        
    return user;
  }
}
