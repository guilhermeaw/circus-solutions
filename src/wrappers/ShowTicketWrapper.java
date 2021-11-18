package wrappers;

import entities.Show;

public class ShowTicketWrapper {
  private Show show;
  private Long quantity = (long) 0;
  private double totalTicketValue = 0;

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

    public double getTotalTicketValue() {
        return totalTicketValue;
    }

    public void setTotalTicketValue(double totalTicketValue) {
        this.totalTicketValue = totalTicketValue;
    }
}
