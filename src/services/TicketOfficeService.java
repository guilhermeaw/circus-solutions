package services;

import org.controlsfx.control.Notifications;

import db.managers.TicketSellManager;
import entities.Show;
import entities.TicketConfig;
import entities.TicketSell;
import javafx.geometry.Pos;
import javafx.util.Duration;

public class TicketOfficeService {
  public static void sellTicket(Show show, TicketConfig ticketConfig) {
    int nextSequential = TicketSellManager.getInstance().getLastSequential(show) + 1;
    
    if (show.getCapacity() <= nextSequential) {
      AlertService.showWarning("Ingressos esgotados");
      return;
    }
    
    TicketSell ticketSell = new TicketSell();
    ticketSell.setShow(show);
    ticketSell.setTicketConfig(ticketConfig);
    ticketSell.setSequential(nextSequential);
    
    TicketSellManager.getInstance().create(ticketSell);

    Notifications ticketNotification = Notifications.create()
                                    .title("Bilheteria - Circus Solution")
                                    .text("Ingresso " + nextSequential + " vendido!")
                                    .position( Pos.BOTTOM_RIGHT )
                                    .hideAfter( Duration.seconds( 10 ) );

    ticketNotification.show();
  }
}
