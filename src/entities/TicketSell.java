package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ticket_sells")
public class TicketSell {
  @Id
  @GenericGenerator(name="incgenerator" , strategy="increment")
  @GeneratedValue(generator="incgenerator")
  @Column(name="id", unique=true, nullable=false)
  private int id;

  @GenericGenerator(name="incgenerator" , strategy="increment")
  @GeneratedValue(generator="incgenerator")
  @Column(name="sequential", nullable=false)
  private int sequential;

  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="ref_show", nullable=false)
  private Show show;

  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="ref_ticket_config", nullable=false)
  private TicketConfig ticketConfig;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSequential() {
        return sequential;
    }

    public void setSequential(int sequential) {
        this.sequential = sequential;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public TicketConfig getTicketConfig() {
        return ticketConfig;
    }

    public void setTicketConfig(TicketConfig ticketConfig) {
        this.ticketConfig = ticketConfig;
    }
}
