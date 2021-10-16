package db.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Audit;

public class AuditManager extends DefaultManager<Audit> {
  private static AuditManager instance;

  public static AuditManager getInstance() {
    if (instance == null){
      instance = new AuditManager();
    }

    return instance;
  }
  
  private AuditManager() {
  }
  
  public List<Audit> getAll() {
    List auditList = new ArrayList();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      auditList = session.createQuery("FROM Audit").list();
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      handleException(e);
    }
      
    return auditList;
  }
}

