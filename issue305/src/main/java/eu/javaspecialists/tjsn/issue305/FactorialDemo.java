/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
