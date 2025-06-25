package eu.javaspecialists.tjsn.issue201;

import java.math.*;

public class FibonacciIterative extends NonCachingFibonacci {
    public BigInteger calculate(int n)
            throws InterruptedException {
        if (n < 0) throw new IllegalArgumentException();
        BigInteger n0 = BigInteger.ZERO;
        BigInteger n1 = BigInteger.ONE;
        for (int i = 0; i < n; i++) {
            if (Thread.interrupted()) throw new InterruptedException();
            BigInteger temp = n1;
            n1 = n1.add(n0);
            n0 = temp;
        }
        return n0;
    }
}
