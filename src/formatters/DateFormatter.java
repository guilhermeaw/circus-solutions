package formatters;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateFormatter {
  public static String format(Date date) {
    String datePattern = "dd/MM/yyyy";
    SimpleDateFormat outputFormat = new SimpleDateFormat(datePattern);

    return outputFormat.format(date);
  }

  public static String format(Timestamp date) {
    String datePattern = "dd/MM/yyyy HH:mm:ss";
    SimpleDateFormat outputFormat = new SimpleDateFormat(datePattern);

    return outputFormat.format(date);
  }
}
