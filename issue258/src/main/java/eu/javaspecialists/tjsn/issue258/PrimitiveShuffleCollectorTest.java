package eu.javaspecialists.tjsn.issue258;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class PrimitiveShuffleCollectorTest {
    private static void printRandom(
            int from, int upto, Supplier<Random> randomSupplier) {
        int[] shuffled = IntStream.range(0, 10)
                .boxed()
                .collect(ShuffleCollector.shuffle(randomSupplier))
                .limit(5)
                .mapToInt(Integer::intValue)
                .toArray();
        System.out.println(Arrays.toString(shuffled));
    }

    public static void main(String... args) {
        printRandom(0, 10, ThreadLocalRandom::current);
        printRandom(0, 10, () -> new Random(0));
    }
}
