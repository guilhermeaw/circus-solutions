package db.managers;

import org.hibernate.Session;

import db.Database;
import entities.TicketConfig;
import utils.ApplicationUtilities;

public class TicketConfigManager extends DefaultManager<TicketConfig> {
  private static TicketConfigManager instance;

  public static TicketConfigManager getInstance() {
    if (instance == null){
      instance = new TicketConfigManager();
    }

    return instance;
  }

  private Database db = Database.getInstance();
  
  private TicketConfigManager() {
  }

  public TicketConfig getLastTicketConfig() {
    TicketConfig ticketConfig = null;
    
    try {
      Session session = db.openSession();
      session.beginTransaction();

      ticketConfig = session.createQuery("from TicketConfig tc where tc.createdDate in (select max(tc2.createdDate) from TicketConfig tc2)", TicketConfig.class)
        .uniqueResult();
        
      session.getTransaction().commit();
      session.close(); 
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
        
    return ticketConfig;
  }
}
