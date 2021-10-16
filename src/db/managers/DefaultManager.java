package db.managers;

import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import db.Database;
import entities.Audit;
import utils.ApplicationUtilities;

public abstract class DefaultManager<T> {
  private static final Logger logger = LogManager.getLogger(DefaultManager.class.getName());
  
  public void create(T value) {
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
  
      session.beginTransaction();
      session.save(value);
      logger.info("Adicionando " + getGenericName());
      // System.out.println("Adicionando " + getGenericName());

      // Auditoria insertAudit = new Auditoria();

      // insertAudit.setDate(new Timestamp(new Date().getTime()));
      // insertAudit.setType("Adicionando " + getGenericName());
      // insertAudit.setUserLogged(ApplicationUtilities.getInstance().getActiveUser());

      // AuditoriaManager.getInstance().create(insertAudit);

      session.getTransaction().commit();
      session.close(); 
    } catch (Exception e) {
      handleException(e);
    }
  };

  public void delete(T value) {
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
  
      session.beginTransaction();
      session.delete(value);
      logger.info("Removendo " + getGenericName());
      session.getTransaction().commit();
      session.close(); 
    } catch (Exception e) {
      handleException(e);
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
      handleException(e);
    }  

    return value;
  }

  public void update(T value) {
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
  
      session.beginTransaction();
      session.update(value);
      logger.info("Editando " + getGenericName());
      session.getTransaction().commit();
      session.close(); 
    } catch (Exception e) {
      handleException(e);
    }
  }

  protected void handleException(Exception e) {
    ApplicationUtilities.getInstance().handleException(e);
  }

  private String getGenericName() { 
    return getGenericClass().getSimpleName();
  }

  private Class<T> getGenericClass() { 
    return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
  }
}
