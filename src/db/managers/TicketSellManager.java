package db.managers;

public class TicketSellManager {
  private static TicketSellManager instance;

  public static TicketSellManager getInstance() {
    if (instance == null){
      instance = new TicketSellManager();
    }

    return instance;
  }
  
  private TicketSellManager() {
  }
}
