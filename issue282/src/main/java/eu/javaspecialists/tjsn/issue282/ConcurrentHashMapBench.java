package eu.javaspecialists.tjsn.issue282;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class ConcurrentHashMapBench {
  public static void main(String... args) {
    for (int i = 0; i < 10; i++) {
      test(false);
      test(true);
    }
  }

  private static void test(boolean parallel) {
    IntStream range = IntStream.range(1, 100_000_000);
    if (parallel) range = range.parallel();
    long time = System.nanoTime();
    try {
      ThreadLocal<Map<Integer, Integer>> maps =
          ThreadLocal.withInitial(() -> {
            Map<Integer, Integer> result =
                new ConcurrentHashMap<>();
            for (int i = 0; i < 1024; i++)
              result.put(i, i * i);
            return result;
          });
      range.map(i -> maps.get().put(i & 1023, i)).sum();
    } finally {
      time = System.nanoTime() - time;
      System.out.printf("%s %dms%n",
          parallel ? "parallel" : "sequential",
          (time / 1_000_000));
    }
  }
}
