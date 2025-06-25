package eu.javaspecialists.tjsn.issue201;

import java.math.*;

class BigIntegerUtils {
    public static BigInteger add(BigInteger... ints) {
        BigInteger sum = ints[0];
        for (int i = 1; i < ints.length; i++) {
            sum = sum.add(ints[i]);
        }
        return sum;
    }

    public static BigInteger[] split(BigInteger x, int m) {
        BigInteger left = x.shiftRight(m);
        BigInteger right = x.subtract(left.shiftLeft(m));
        return new BigInteger[]{left, right};
    }
}
