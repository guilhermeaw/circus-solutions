package db.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Login;

public class LoginManager extends DefaultManager<Login> {
  private static LoginManager instance;

  public static LoginManager getInstance() {
    if (instance == null){
      instance = new LoginManager();
    }

    return instance;
  }
  
  private LoginManager() {
  }
  
  public List<Login> getAll() {
    List loginList = new ArrayList();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      loginList = session.createQuery("FROM Login").list();
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      handleException(e);
    }
      
    return loginList;
  }
}

