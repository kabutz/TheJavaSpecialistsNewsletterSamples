package eu.javaspecialists.tjsn.issue201;

import java.math.*;

public abstract class NonCachingFibonacci extends Fibonacci {
    protected NonCachingFibonacci() {
        super(null);
    }

    public final BigInteger doActualCalculate(int n)
            throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    public abstract BigInteger calculate(int n)
            throws InterruptedException;
}
