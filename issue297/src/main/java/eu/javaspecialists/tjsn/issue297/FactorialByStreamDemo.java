package eu.javaspecialists.tjsn.issue297;

import java.math.*;
import java.util.stream.*;

public class FactorialByStreamDemo {
    public static void main(String... args) {
        ForkJoinPoolBench.test(
                () -> IntStream.rangeClosed(1, 200_000)
                        .mapToObj(BigInteger::valueOf)
                        .reduce(BigInteger.ONE, BigInteger::multiply),
                new DefaultStatsListener("sequentialFactorial"));

        ForkJoinPoolBench.test(
                () -> IntStream.rangeClosed(1, 200_000)
                        .parallel()
                        .mapToObj(BigInteger::valueOf)
                        .reduce(BigInteger.ONE, BigInteger::multiply),
                new DefaultStatsListener("parallelFactorial"));
    }
}
