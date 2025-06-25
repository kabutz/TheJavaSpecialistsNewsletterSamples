package eu.javaspecialists.tjsn.issue289;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class SortedStreamOfSortedStreams {
    private static final int SIZE = 5;

    public static void main(String... args) {
        List<Stream<Integer>> streams = List.of(
                generateSortedRandom(SIZE),
                generateSortedRandom(SIZE),
                generateSortedRandom(SIZE),
                generateSortedRandom(SIZE)
        );

        Stream<Integer> numbers = StreamSupport.stream(
                new MergingSortedSpliterator<>(streams), false
        );
        numbers.forEach(System.out::println);
    }

    private static Stream<Integer> generateSortedRandom(int size) {
        return ThreadLocalRandom.current().ints(size, 0, size * 4)
                .parallel()
                .sorted()
                .boxed();
    }
}
