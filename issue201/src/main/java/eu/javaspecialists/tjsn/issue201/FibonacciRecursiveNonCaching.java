package eu.javaspecialists.tjsn.issue201;

import java.math.*;

public class FibonacciRecursiveNonCaching
        extends NonCachingFibonacci {
    public BigInteger calculate(int n)
            throws InterruptedException {
        if (Thread.interrupted()) throw new InterruptedException();
        if (n < 0) throw new IllegalArgumentException();
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;
        return calculate(n - 1).add(calculate(n - 2));
    }
}
