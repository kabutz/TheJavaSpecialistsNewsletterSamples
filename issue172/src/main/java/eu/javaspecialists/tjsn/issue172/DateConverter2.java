package eu.javaspecialists.tjsn.issue172;

import java.text.*;
import java.util.Date;

public class DateConverter2 {
  private static final ThreadLocal<DateFormat> tl =
      new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
          return new SimpleDateFormat("yyyy/MM/dd");
        }
      };

  public void testConvert(String date) {
    try {
      DateFormat formatter = tl.get();
      Date d = formatter.parse(date);
      String newDate = formatter.format(d);
      if (!date.equals(newDate)) {
        System.out.println(date + " converted to " + newDate);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
