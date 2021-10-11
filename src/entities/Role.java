package entities;

public enum Role {
  OPERATOR("Operador", 0),
  MANAGER("Gerente", 1),
  ADMIN("Administrador", 2);

  private final String name;
  private final int level;

  private Role(String name, int level) {
    this.name = name;
    this.level = level;
  }

  public String getName() {
    return name;
  }

  public int getLevel() {
    return level;
  }

  @Override
  public String toString() {
    return name;
  }
}
