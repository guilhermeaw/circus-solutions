package entities;

public enum Operation {
  VIEW("view"),
  INSERT("insert"),
  MODIFY("modify"),
  DELETE("delete");

  private final String name;

  private Operation(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
