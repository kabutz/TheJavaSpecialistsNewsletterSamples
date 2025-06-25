package eu.javaspecialists.tjsn.issue236;

import java.math.*;

public class Fibonacci {
    public BigInteger f(int n) {
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;
        if (n % 2 == 1) { // F(2n-1) = F(n-1)^2 + F(n)^2
            n = (n + 1) / 2;
            BigInteger fn_1 = f(n - 1);
            BigInteger fn = f(n);
            return fn_1.multiply(fn_1).add(fn.multiply(fn));
        } else { // F(2n) = ( 2 F(n-1) + F(n) ) F(n)
            n = n / 2;
            BigInteger fn_1 = f(n - 1);
            BigInteger fn = f(n);
            return fn_1.shiftLeft(1).add(fn).multiply(fn);
        }
    }

    public BigInteger f_slow(int n) {
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;
        return f_slow(n - 1).add(f_slow(n - 2));
    }
}
