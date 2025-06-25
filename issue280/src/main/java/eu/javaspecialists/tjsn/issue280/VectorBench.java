package eu.javaspecialists.tjsn.issue280;

import java.util.*;
import java.util.stream.*;

public class VectorBench {
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
            ThreadLocal<List<Integer>> lists =
                    ThreadLocal.withInitial(() -> {
                        List<Integer> result = new Vector<>();
                        for (int i = 0; i < 1024; i++) result.add(i);
                        return result;
                    });
            range.map(i -> lists.get().get(i & 1023)).sum();
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("%s %dms%n",
                    parallel ? "parallel" : "sequential", (time / 1_000_000));
        }
    }
}
