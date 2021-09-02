package validators;

public class EmptyValidator {
  public static boolean validate(String value) {
    return value != null && !value.trim().isEmpty();
  }
}
