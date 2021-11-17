package wrappers;

import entities.Show;

public class ShowTicketWrapper {
  private Show show;
  private Long quantity = (long) 0;
  private int ticketPrice = 0;

  public ShowTicketWrapper() {}

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
