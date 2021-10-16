package db.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Error;

public class ErrorManager extends DefaultManager<Error> {
  private static ErrorManager instance;

  public static ErrorManager getInstance() {
    if (instance == null){
      instance = new ErrorManager();
    }

    return instance;
  }
  
  private ErrorManager() {
  }
  
  public List<Error> getAll() {
    List errorList = new ArrayList();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      errorList = session.createQuery("FROM Error").list();
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      handleException(e);
    }
      
    return errorList;
  }
}

