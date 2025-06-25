package eu.javaspecialists.tjsn.issue297;

import java.util.*;
import java.util.concurrent.*;

public class ArraySortBenchTest {
    public static void main(String... args) {
        int[] array = ThreadLocalRandom.current()
                .ints(100_000_000).toArray();

        for (int i = 0; i < 3; i++) {
            int[] sequentialToSort = array.clone();
            ForkJoinPoolBench.test(
                    () -> Arrays.sort(sequentialToSort),
                    new DefaultStatsListener("sequentialSort" + i));
        }

        for (int i = 0; i < 3; i++) {
            int[] parallelToSort = array.clone();
            ForkJoinPoolBench.test(
                    () -> Arrays.parallelSort(parallelToSort),
                    new DefaultStatsListener("parallelSort" + i));
        }
    }
}
