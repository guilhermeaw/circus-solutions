package formatters;

public class CpfFormatter {
  public static String format(String cpf) {
    return cpf.replaceAll("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})", "$1.$2.$3-$4");
  }
}
