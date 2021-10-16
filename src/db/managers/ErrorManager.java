package db.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Error;
import utils.ApplicationUtilities;

public class ErrorManager {
  private static ErrorManager instance;

  public static ErrorManager getInstance() {
    if (instance == null){
      instance = new ErrorManager();
    }

    return instance;
  }
  
  private ErrorManager() {
  }

  public void create(Error value) {
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
  
      session.beginTransaction();
      session.save(value);
      session.getTransaction().commit();
      session.close(); 
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e, false);
    }
  };
  
  public List<Error> getAll() {
    List<Error> errorList = new ArrayList<Error>();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      errorList = session.createQuery("FROM Error", Error.class).list();
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
      
    return errorList;
  }
}

