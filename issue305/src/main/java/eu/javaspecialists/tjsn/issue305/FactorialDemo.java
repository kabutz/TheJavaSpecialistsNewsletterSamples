package eu.javaspecialists.tjsn.issue305;

import java.math.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class FactorialDemo {
    public static void main(String... args) {
        record Pair(String description,
                    BinaryOperator<BigInteger> operator) {
        }
        List<Pair> multiplies = List.of(
                new Pair("multiply", BigInteger::multiply),
                new Pair("parallelMultiply", BigInteger::parallelMultiply)
        );
        List<Function<BinaryOperator<BigInteger>, Factorial>> funcs =
                List.of(
                        FactorialSequential::new,
                        FactorialCompletableFuture::new,
                        FactorialForkJoin::new
                );
        for (var func : funcs) {
            var factorial = func.apply(BigInteger::multiply);
            System.out.println(factorial.getClass());
            IntStream.rangeClosed(0, 10)
                    .mapToObj(factorial::calculate)
                    .forEach(System.out::println);
            System.out.println();
        }

        for (int i = 0; i < 10; i++) {
            for (var func : funcs) {
                for (var multiply : multiplies) {
                    Factorial factorial = func.apply(multiply.operator());
                    System.out.print(factorial.getClass() + " with " +
                            multiply.description());
                    long time = System.nanoTime();
                    try {
                        BigInteger fac1m = factorial.calculate(1_000_000);
                    } finally {
                        time = System.nanoTime() - time;
                        System.out.printf(" time = %dms%n",
                                (time / 1_000_000));
                    }
                }
            }
            System.out.println();
        }
    }
}
