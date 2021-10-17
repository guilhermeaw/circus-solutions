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
import entities.Error;
import entities.User;
import filters.data.ErrorFilter;
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

  public List<Error> getByFilter(ErrorFilter filter) {
    List<Error> errorList = new ArrayList<Error>();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();

      StringBuilder sql = new StringBuilder("FROM Error");
      Map<String,Object> parameters = composeFilterConditions(filter, sql);

      Query<Error> query = session.createQuery(sql.toString(), Error.class);

      Set<String> parameterSet = parameters.keySet();
      for (Iterator<String> it = parameterSet.iterator(); it.hasNext();) {
        String parameter = it.next();
        query.setParameter(parameter, parameters.get(parameter));
      }

      errorList = query.list();
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
      
    return errorList;
  }

  private Map<String,Object> composeFilterConditions(ErrorFilter filter, StringBuilder sql) {
    List<String> conditions = new ArrayList<String>();
    Map<String,Object> parameters = new HashMap<String,Object>();

    User user = filter.getUser();
    Date startDate = filter.getStartDate();
    Date endDate = filter.getEndDate();

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

