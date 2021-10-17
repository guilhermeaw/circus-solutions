package db.managers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;

import db.Database;
import entities.Audit;
import entities.User;
import filters.data.AuditFilter;

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
    List<Audit> auditList = new ArrayList<Audit>();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      auditList = session.createQuery("FROM Audit", Audit.class).list();
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      handleException(e);
    }
      
    return auditList;
  }

  public List<Audit> getByFilter(AuditFilter filter) {
    List<Audit> auditList = new ArrayList<Audit>();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();

      StringBuilder sql = new StringBuilder("FROM Audit");
      Map<String,Object> parameters = composeFilterConditions(filter, sql);

      Query<Audit> query = session.createQuery(sql.toString(), Audit.class);

      Set<String> parameterSet = parameters.keySet();
      for (Iterator<String> it = parameterSet.iterator(); it.hasNext();) {
        String parameter = it.next();
        query.setParameter(parameter, parameters.get(parameter));
      }

      auditList = query.list();
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      handleException(e);
    }
      
    return auditList;
  }

  private Map<String,Object> composeFilterConditions(AuditFilter filter, StringBuilder sql) {
    List<String> conditions = new ArrayList<String>();
    Map<String,Object> parameters = new HashMap<String,Object>();

    String type = filter.getType();
    User user = filter.getUser();
    Date startDate = filter.getStartDate();
    Date endDate = filter.getEndDate();

    if (type != null) {
      conditions.add(" type = :type");
      parameters.put("type", type);
    }

    if (user != null) {
      conditions.add(" ref_user = :user");
      parameters.put("user", user.getId());
    }

    if (startDate != null) {
      conditions.add(" date >= :startDate");
      parameters.put("startDate", startDate);
    }

    if (endDate != null) {
      conditions.add(" date <= :endDate");
      parameters.put("endDate", endDate);
    }

    for (int i = 0; i < conditions.size(); i++) {
      String preffix = i == 0 ? " where" : " and";
      String condition = conditions.get(i);

      sql.append(preffix).append(condition);
    }

    return parameters;
  }
}

