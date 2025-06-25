package eu.javaspecialists.tjsn.issue201;

import java.math.*;

public abstract class Fibonacci {
    private final FibonacciCache cache;

    protected Fibonacci(FibonacciCache cache) {
        this.cache = cache;
    }

    public Fibonacci() {
        this(null);
    }

    public BigInteger calculate(int n)
            throws InterruptedException {
        if (cache == null) return doActualCalculate(n);

        BigInteger result = cache.get(n);
        if (result == null) {
            cache.put(n, result = doActualCalculate(n));
        }
        return result;
    }

    protected abstract BigInteger doActualCalculate(int n)
            throws InterruptedException;
}
