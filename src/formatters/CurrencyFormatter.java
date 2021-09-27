package formatters;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
  public static String format(String value) {
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    return numberFormat.format(getAmount(value));
  }

  public static double getAmount(String value) {
    double result = 0;

    if (value != null && !value.isEmpty()) {
      if (value.contains(".")) {
        String decimalPart = value.split("\\.")[1];

        if (decimalPart.length() == 1) {
          value += "0";
        }
      }

      String plainText = value.replaceAll("[^0-9]", "");

      while (plainText.length() < 3) {
        plainText = "0" + plainText;
      }

      StringBuilder builder = new StringBuilder(plainText);
      builder.insert(plainText.length() - 2, ".");

      result = Double.parseDouble(builder.toString());
    }

    return result;
  }
}
