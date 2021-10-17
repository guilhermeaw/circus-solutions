package entities;

public enum Operation {
  VIEW("VIEW"),
  INSERT("INSERT"),
  UPDATE("UPDATE"),
  DELETE("DELETE");

  private final String name;

  private Operation(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
