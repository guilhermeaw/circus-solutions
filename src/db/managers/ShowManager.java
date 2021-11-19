package db.managers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entities.City;
import entities.Show;
import entities.User;
import filters.data.ShowFilter;

import org.hibernate.Session;
import org.hibernate.query.Query;

import db.Database;
import utils.ApplicationUtilities;

public class ShowManager extends DefaultManager<Show> {
  private static ShowManager instance;

  private ShowManager() {
  }

  public static ShowManager getInstance() {
    if (instance == null){
      instance = new ShowManager();
    }

    return instance;
  }

  @Override
  public Show getById(Class<Show> class1, int id) {
      Show show = super.getById(class1, id);
      City city = CityManager.getInstance().getById(show.getCityId());
      
      show.setCity(city);

      return show;
  }

  public List<Long> getIdsOfCitiesWithShows() {
    List<Long> citiesIds = new ArrayList<Long>();
    
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
  
      session.beginTransaction();
      Object result = session.createQuery("select distinct s.cityId from Show s").list();

      if (result != null) {
        citiesIds = (List<Long>) result;
      }

      session.getTransaction().commit();
      session.close(); 
    } catch (Exception e) {
      handleException(e);
    }  

    return citiesIds;
  }

  public Show getActiveShow() {
    Show value = null;
    
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
  
      session.beginTransaction();
      value = session.createQuery("from Show s where s.isShowActive = :active", Show.class)
        .setParameter("active", true)
        .uniqueResult();

      if (value != null) {
        City city = CityManager.getInstance().getById(value.getCityId());
        value.setCity(city);
      }

      session.getTransaction().commit();
      session.close(); 
    } catch (Exception e) {
      handleException(e);
    }  

    return value;
  }

  public List<Show> getAll() {
    List<Show> showList = new ArrayList<Show>();
    
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      showList = session.createQuery("FROM Show", Show.class).list();

      for (Show show : showList) {
        show.setCity(CityManager.getInstance().getById(show.getCityId()));
      }
      
      session.getTransaction().commit();
      session.close();

    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
      
    return showList;
  }

  public List<Show> getByFilter(ShowFilter filter) {
    List<Show> showsList = new ArrayList<Show>();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();

      StringBuilder sql = new StringBuilder("FROM Show");
      Map<String,Object> parameters = composeFilterConditions(filter, sql);

      Query<Show> query = session.createQuery(sql.toString(), Show.class);

      Set<String> parameterSet = parameters.keySet();
      for (Iterator<String> it = parameterSet.iterator(); it.hasNext();) {
        String parameter = it.next();
        query.setParameter(parameter, parameters.get(parameter));
      }

      showsList = query.list();

      for (Show show : showsList) {
        show.setCity(CityManager.getInstance().getById(show.getCityId()));
      }

      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
      
    return showsList;
  }

  private Map<String,Object> composeFilterConditions(ShowFilter filter, StringBuilder sql) {
    List<String> conditions = new ArrayList<String>();
    Map<String,Object> parameters = new HashMap<String,Object>();

    User author = filter.getAuthor();
    City city = filter.getCity();
    Date startDate = filter.getStartDate();
    Date endDate = filter.getEndDate();

    if (author != null) {
      conditions.add(" ref_user = :author");
      parameters.put("author", author.getId());
    }

    if (city != null) {
      conditions.add(" ref_city = :city");
      parameters.put("city", city.getId());
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
