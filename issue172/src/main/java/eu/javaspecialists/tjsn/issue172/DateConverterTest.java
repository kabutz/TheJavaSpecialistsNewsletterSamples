package eu.javaspecialists.tjsn.issue172;

import java.util.concurrent.*;

public class DateConverterTest {
  public static void main(String... args) {
    ExecutorService pool = Executors.newCachedThreadPool();
    convert(pool, "1971/12/04");
    convert(pool, "2001/09/02");
  }

  private static void convert(ExecutorService pool, final String date) {
    pool.submit(new Runnable() {
      public void run() {
        DateConverter1 dc = new DateConverter1();
        while (true) {
          dc.testConvert(date);
        }
      }
    });
  }
}
