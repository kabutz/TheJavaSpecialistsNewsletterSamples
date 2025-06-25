package eu.javaspecialists.tjsn.issue305;

import java.math.*;
import java.util.concurrent.*;
import java.util.function.*;

import static java.util.concurrent.CompletableFuture.*;

public class FactorialCompletableFuture
        extends AbstractFactorial {
    public FactorialCompletableFuture(
            BinaryOperator<BigInteger> multiply) {
        super(multiply);
    }

    @Override
    public BigInteger calculate(int n) {
        if (n < 0) throw new IllegalArgumentException("n < 0");
        if (n == 0) return BigInteger.ONE;
        return calculate(1, n).join();
    }

    private CompletableFuture<BigInteger> calculate(int from, int to) {
        if (from == to) return completedFuture(BigInteger.valueOf(from));
        int mid = (from + to) >>> 1;
        return calculate(from, mid).thenCombineAsync(
                calculate(mid + 1, to), this::multiply);
    }
}
