package eu.javaspecialists.tjsn.issue297;

import java.util.*;
import java.util.concurrent.*;

public class DefaultStatsListener implements
    ForkJoinPoolBench.StatsListener {
  private final String description;

  public DefaultStatsListener(String description) {
    this.description = description;
  }

  public void result(long realTime,
                     LongSummaryStatistics userTimeStats,
                     LongSummaryStatistics cpuTimeStats,
                     LongSummaryStatistics allocationStats) {
    long userTime = userTimeStats.getSum();
    long sysTime = cpuTimeStats.getSum() - userTime;
    long bytes = allocationStats.getSum();

    System.out.println("Results for " + description);
    System.out.println("real  " + formatTime(realTime));
    System.out.println("user  " + formatTime(userTime));
    System.out.println("sys   " + formatTime(sysTime));
    System.out.println("mem   " + formatMemory(bytes));
  }

  private static String formatMemory(double bytes) {
    double val;
    String unitStr;
    if (bytes < 1024) {
      val = bytes;
      unitStr = "B";
    } else if (bytes < 1024 * 1024) {
      val = bytes / 1024;
      unitStr = "KB";
    } else if (bytes < 1024 * 1024 * 1024) {
      val = bytes / (1024 * 1024);
      unitStr = "MB";
    } else if (bytes < 1024 * 1024 * 1024 * 1024L) {
      val = bytes / (1024 * 1024 * 1024L);
      unitStr = "GB";
    } else {
      val = bytes / (1024 * 1024 * 1024 * 1024L);
      unitStr = "TB";
    }
    return String.format(Locale.US, "%.1f%s", val, unitStr);
  }

  private static String formatTime(long nanos) {
    if (nanos < 0) nanos = 0;
    long timeInMs = TimeUnit.NANOSECONDS.toMillis(nanos);
    long minutes = timeInMs / 60_000;
    double remainingMs = (timeInMs % 60_000) / 1000.0;
    return String.format(Locale.US, "%dm%.3fs", minutes, remainingMs);
  }
}
