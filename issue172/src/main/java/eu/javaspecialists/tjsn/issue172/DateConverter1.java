package eu.javaspecialists.tjsn.issue172;

import java.text.*;
import java.util.Date;

public class DateConverter1 {
  private static final DateFormat df =
      new SimpleDateFormat("yyyy/MM/dd");

  public void testConvert(String date) {
    try {
      Date d = df.parse(date);
      String newDate = df.format(d);
      if (!date.equals(newDate)) {
        System.out.println(date + " converted to " + newDate);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
