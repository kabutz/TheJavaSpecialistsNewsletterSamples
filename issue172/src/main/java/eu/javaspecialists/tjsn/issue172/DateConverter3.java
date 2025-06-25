package eu.javaspecialists.tjsn.issue172;

import java.lang.ref.SoftReference;
import java.text.*;
import java.util.Date;

public class DateConverter3 {
  private static final ThreadLocal<SoftReference<DateFormat>> tl
      = new ThreadLocal<SoftReference<DateFormat>>();

  private static DateFormat getDateFormat() {
    SoftReference<DateFormat> ref = tl.get();
    if (ref != null) {
      DateFormat result = ref.get();
      if (result != null) {
        return result;
      }
    }
    DateFormat result = new SimpleDateFormat("yyyy/MM/dd");
    ref = new SoftReference<DateFormat>(result);
    tl.set(ref);
    return result;
  }

  public void testConvert(String date) {
    try {
      DateFormat formatter = getDateFormat();
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
