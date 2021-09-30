package db.managers;

import javax.persistence.NoResultException;

import org.hibernate.Session;

import db.Database;
import entities.Show;
import entities.TicketSell;
import utils.ApplicationUtilities;

public class TicketSellManager extends DefaultManager<TicketSell> {
  private static TicketSellManager instance;
  private Database db = Database.getInstance();

  public static TicketSellManager getInstance() {
    if (instance == null){
      instance = new TicketSellManager();
    }

    return instance;
  }
  
  private TicketSellManager() {
  }

  public int getLastSequential(Show show) {
    int sequential = 0;
    
    try {
      Session session = db.openSession();
      session.beginTransaction();

      Object result = session.createQuery("select max(ts.sequential) from TicketSell ts inner join ts.show s where s.id = :show")
        .setParameter("show", show.getId())
        .uniqueResult();

      if (result != null) {
        sequential = (Integer) result;
      }
        
      session.getTransaction().commit();
      session.close(); 
    } catch (NoResultException nre) {
      // Ignora, pois aceitamos um null como retorno
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
        
    return sequential;
  }
}
