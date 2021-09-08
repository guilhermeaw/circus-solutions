package formatters;

public class PhoneFormatter {
  public static String format(String phone) {
    return phone.replaceFirst("(\\d{2})(\\d{5})(\\d+)", "($1) $2-$3");
  }
}
