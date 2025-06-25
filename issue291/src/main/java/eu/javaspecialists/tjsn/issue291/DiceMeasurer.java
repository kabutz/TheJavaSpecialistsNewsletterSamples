package eu.javaspecialists.tjsn.issue291;

import java.util.*;
import java.util.concurrent.atomic.*;

public class DiceMeasurer {
    private static final int RUNS = 600_000_000;

    private static final LongAccumulator best =
            new LongAccumulator(Long::min, Long.MAX_VALUE);

    public static void main(String... args) {
        for (int i = 0; i < 20; i++) {
            test();
        }
        System.out.printf("best time = %dms%n",
                (best.longValue() / 1_000_000));
    }

    private static void test() {
        long time = System.nanoTime();
        try {
            // Dice dice = new DiceFair(); // 1433 ms
            // Dice dice = new DiceFair(new SecureRandom()); // 85495 ms
            // Dice dice = new DiceFast(); // 1001 ms
            Dice dice = new DiceFastFixedMod(); // 690 ms
            // Dice dice = new DiceVeryFast(); // 372 ms
            int[] results = new int[6];
            for (int i = 0; i < RUNS; i++) {
                results[dice.roll() - 1]++;
            }
            System.out.println("results = " + Arrays.toString(results));
            int[] diffs = new int[6];
            for (int i = 0; i < diffs.length; i++) {
                diffs[i] = results[i] - RUNS / 6;
            }
            System.out.println("diffs = " + Arrays.toString(diffs));
        } finally {
            time = System.nanoTime() - time;
            best.accumulate(time);
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}
