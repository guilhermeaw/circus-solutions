package entities;

public enum Pane {
  ARTISTS("Artists"),
  ATTRACTIONS("Attractions"),
  OCCUPATIONS("Occupations"),
  SHOWS("Shows"),
  TICKET_OFFICE("TicketOffice"),
  TICKETS("Tickets");

  private final String name;

  private Pane(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
