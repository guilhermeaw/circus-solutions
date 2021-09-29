package db.managers;

import java.util.ArrayList;
import java.util.List;

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

  // @Override
  // public Show getById(Class<Show> class1, int id) {
  //     Show show = super.getById(class1, id);
  //     City city = CityManager.getInstance().getById(show.getCityId());
      
  //     show.setCity(city);

  //     return show;
  // }

  public List<Show> getAll() {
    List showList = new ArrayList();
    
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      showList = session.createQuery("FROM Show").list();
      session.getTransaction().commit();
      session.close();

    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
      
    return showList;
  }
}
