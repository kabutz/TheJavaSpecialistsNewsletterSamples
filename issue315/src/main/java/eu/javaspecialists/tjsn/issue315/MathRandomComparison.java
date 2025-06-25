package eu.javaspecialists.tjsn.issue315;

import java.util.stream.*;

public class MathRandomComparison {
    private static double randomValue() {
        return Math.random();
    }

    public static void main(String... args) {
        System.out.println(IntStream.range(0, 100_000_000)
                .mapToDouble(i -> randomValue())
                .sum());
    }
}
