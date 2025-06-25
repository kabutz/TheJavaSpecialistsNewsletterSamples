package eu.javaspecialists.tjsn.issue305;

import java.math.*;
import java.util.function.*;

public class FactorialSequential extends AbstractFactorial {
    public FactorialSequential(BinaryOperator<BigInteger> multiply) {
        super(multiply);
    }

    @Override
    public BigInteger calculate(int n) {
        if (n < 0) throw new IllegalArgumentException("n < 0");
        return calculate(0, n);
    }

    private BigInteger calculate(int from, int to) {
        if (from == to) {
            if (from == 0) return BigInteger.ONE;
            return BigInteger.valueOf(from);
        }
        int mid = (from + to) >>> 1;
        BigInteger left = calculate(from, mid);
        BigInteger right = calculate(mid + 1, to);
        return multiply(left, right);
    }
}
