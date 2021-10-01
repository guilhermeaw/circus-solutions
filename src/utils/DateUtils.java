package utils;

import java.time.LocalDate;
import java.sql.Date;

public class DateUtils {
    public static LocalDate getLocalDateByDate(Date date) {
        LocalDate localDate = date.toLocalDate();

        return localDate;
    }

    public static Date getDateByLocalDate(LocalDate localDate) {
       Date date = Date.valueOf(localDate);

       return date;
    }
}