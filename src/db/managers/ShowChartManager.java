package db.managers;

import java.util.ArrayList;
import java.util.List;

import entities.Show;

import org.hibernate.Session;
import db.Database;
import utils.ApplicationUtilities;
import wrappers.ShowTicketWrapper;

public class ShowChartManager {
  private static ShowChartManager instance;

  private ShowChartManager() {
  }

  public static ShowChartManager getInstance() {
    if (instance == null) {
      instance = new ShowChartManager();
    }

    return instance;
  }

  public List<ShowTicketWrapper> getShowsPerTicketSellQuantity() {
    List<Show> showList = new ArrayList<Show>();
    List<ShowTicketWrapper> showTicketWrapperList = new ArrayList<ShowTicketWrapper>();
    
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      showList = session.createQuery("FROM Show s order by s.date", Show.class).setMaxResults(5).list();
      
      for (Show show : showList) {
        show.setCity(CityManager.getInstance().getById(show.getCityId()));

        Long ticketSellQuantity = (long) 0;
        Object quantityResult = session.createQuery("select count(ts.id) FROM TicketSell ts inner join ts.show s where s.id = :show")
          .setParameter("show", show.getId())
          .getSingleResult();

        if (quantityResult != null) {
          ticketSellQuantity = (Long) quantityResult;
        }

        ShowTicketWrapper showTicketWrapper = new ShowTicketWrapper();
        showTicketWrapper.setQuantity(ticketSellQuantity);
        showTicketWrapper.setShow(show);

        showTicketWrapperList.add(showTicketWrapper);
      }
      
      session.getTransaction().commit();
      session.close();

    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
      
    return showTicketWrapperList;
  }

  public List<ShowTicketWrapper> getShowsPerTotalTicketValue() {
    List<Show> showList = new ArrayList<Show>();
    List<ShowTicketWrapper> showTicketWrapperList = new ArrayList<ShowTicketWrapper>();
    
    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      showList = session.createQuery("FROM Show s order by s.date", Show.class).setMaxResults(5).list();
      
      for (Show show : showList) {
        show.setCity(CityManager.getInstance().getById(show.getCityId()));

        List<Double> ticketValues = new ArrayList<Double>();
        Object valueResult = session.createQuery("select tc.value FROM TicketSell ts inner join ts.show s inner join ts.ticketConfig tc where s.id = :show")
          .setParameter("show", show.getId())
          .list();

        if (valueResult != null) {
          ticketValues = (List<Double>) valueResult;
        }

        double totalTicketValue = ticketValues.stream().mapToDouble(d->d).sum();
          
        ShowTicketWrapper showTicketWrapper = new ShowTicketWrapper();
        showTicketWrapper.setTotalTicketValue(totalTicketValue);;
        showTicketWrapper.setShow(show);

        showTicketWrapperList.add(showTicketWrapper);
      }
      
      session.getTransaction().commit();
      session.close();

    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
      
    return showTicketWrapperList;
  }
}
