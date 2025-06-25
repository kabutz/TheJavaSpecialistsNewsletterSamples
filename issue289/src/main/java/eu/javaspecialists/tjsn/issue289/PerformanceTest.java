package eu.javaspecialists.tjsn.issue289;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class PerformanceTest {
    private static final int SIZE = 10_000_000;
    private static final
    List<Function<List<Stream<Integer>>, Stream<Integer>>> MERGERS =
            List.of(
                    s -> s.stream()
                            .flatMap(Function.identity())
                            .sorted(),
                    s -> s.stream()
                            .flatMap(Function.identity())
                            .parallel()
                            .sorted(),
                    s -> StreamSupport.stream(
                            new MergingSortedSpliterator<>(s), false
                    ));

    public static void main(String... args) {
        for (int i = 0; i < 10; i++) {
            test();
            System.out.println();
        }
    }

    private static void test() {
        MERGERS.forEach(merger -> {
            List<Stream<Integer>> streams = makeStreams();
            long time = System.nanoTime();
            try {
                Stream<Integer> numbers = merger.apply(streams);
                numbers.forEach(i -> {
                });
            } finally {
                time = System.nanoTime() - time;
                System.out.printf("time = %dms%n", (time / 1_000_000));
            }
        });
    }

    private static List<Stream<Integer>> makeStreams() {
        return Stream.generate(() -> generateSorted(SIZE))
                .limit(10).collect(Collectors.toList());
    }

    private static Stream<Integer> generateSorted(int size) {
        return IntStream.range(0, size).boxed();
    }
}
