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

package eu.javaspecialists.tjsn.issue297;

import java.math.*;
import java.util.concurrent.*;
import java.util.function.*;

public class FactorialByCompletableFutureDemo {
    private static final
    BinaryOperator<CompletableFuture<BigInteger>> SERIAL =
            (a, b) -> a.thenCombine(b, BigInteger::multiply);
    private static final
    BinaryOperator<CompletableFuture<BigInteger>> PARALLEL =
            (a, b) -> a.thenCombineAsync(b, BigInteger::multiply);

    private static BigInteger factorial(
            int n, BinaryOperator<CompletableFuture<BigInteger>> op) {
        return factorial(0, n, op).join();
    }

    private static CompletableFuture<BigInteger> factorial(
            int from, int to,
            BinaryOperator<CompletableFuture<BigInteger>> op) {
        if (from == to) {
            BigInteger result = from == 0 ? BigInteger.ONE :
                    BigInteger.valueOf(from);
            return CompletableFuture.completedFuture(result);
        }
        int mid = (from + to) >>> 1;
        return op.apply(factorial(from, mid, op),
                factorial(mid + 1, to, op));
    }

    public static void main(String... args) {
        ForkJoinPoolBench.test(
                () -> factorial(2_000_000, SERIAL),
                new DefaultStatsListener("sequentialFactorial"));

        ForkJoinPoolBench.test(
                () -> factorial(2_000_000, PARALLEL),
                new DefaultStatsListener("parallelFactorial"));
    }
}
