package db.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import entities.City;
import entities.Show;

import org.hibernate.Session;
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

  public Show getActiveShow() {
    Show value = null;
    
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
  
      session.beginTransaction();
      value = session.createQuery("from Show s where s.isShowActive = :active", Show.class)
        .setParameter("active", true)
        .getSingleResult();

      if (value != null) {
        City city = CityManager.getInstance().getById(value.getCityId());
        value.setCity(city);
      }

      session.getTransaction().commit();
      session.close(); 
    } catch (NoResultException nre) {
      // Ignora, pois aceitamos um null como retorno
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
      showList = session.createQuery("FROM Show").list();

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
}
