package entities;

public enum Pane {
  ADMIN("Admin"),
  ARTISTS("Artists"),
  ATTRACTIONS("Attractions"),
  GRAPHICS("Graphics"),
  OCCUPATIONS("Occupations"),
  SHOWS("Shows"),
  TICKET_OFFICE("TicketOffice"),
  TICKETS("Tickets"),
  USERS("Users");

  private final String name;

  private Pane(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
