package db.managers;

import java.util.List;

import org.hibernate.Session;

import db.Database;
import utils.ApplicationUtilities;

public abstract class DefaultManager<T> {
  public void create(T value) {
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
  
      session.beginTransaction();
      session.save(value);
      session.getTransaction().commit();
      session.close(); 
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
  };

  public void delete(T value) {
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
  
      session.beginTransaction();
      session.delete(value);
      session.getTransaction().commit();
      session.close(); 
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
  }

  public T getById( Class<T> class1, int id ) {
    T value = null;
    
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
  
      session.beginTransaction();
      value = session.get(class1, id);
      session.getTransaction().commit();
      session.close(); 
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }  

    return value;
  }

  public void update(T value) {
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
  
      session.beginTransaction();
      session.update(value);
      session.getTransaction().commit();
      session.close(); 
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
  }
}
