package eu.javaspecialists.tjsn.issue258;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class ShuffleCollector {
    public static <T> Collector<T, ?, Stream<T>> shuffle() {
        return shuffle(ThreadLocalRandom::current);
    }

    public static <T> Collector<T, ?, Stream<T>> shuffle(
            Supplier<? extends Random> random) {
        return Collectors.collectingAndThen(Collectors.toList(),
                ts -> {
                    Collections.shuffle(ts, random.get());
                    return ts.stream();
                });
    }
}
