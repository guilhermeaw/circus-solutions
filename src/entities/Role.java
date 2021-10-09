package entities;

public enum Role {
  OPERATOR("Operador"),
  MANAGER("Gerente"),
  ADMIN("Administrador");

  private final String name;

  private Role(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
