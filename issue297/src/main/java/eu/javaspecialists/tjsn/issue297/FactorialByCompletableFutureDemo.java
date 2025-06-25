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
