package eu.javaspecialists.tjsn.issue201;

import java.math.*;
import java.util.concurrent.*;

import static eu.javaspecialists.tjsn.issue201.BigIntegerUtils.*;

public class ParallelKaratsuba implements Karatsuba {
    public static final String THRESHOLD_PROPERTY_NAME =
            "eu.javaspecialists.tjsn.math.numbers.ParallelKaratsubaThreshold";
    private static final int THRESHOLD = Integer.getInteger(
            THRESHOLD_PROPERTY_NAME, 1000);
    private final ForkJoinPool pool;

    public ParallelKaratsuba(ForkJoinPool pool) {
        this.pool = pool;
    }

    public BigInteger multiply(BigInteger x, BigInteger y) {
        return pool.invoke(new KaratsubaTask(x, y));
    }

    private static class KaratsubaTask
            extends RecursiveTask<BigInteger> {
        private final BigInteger x, y;

        public KaratsubaTask(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }

        protected BigInteger compute() {
            int m = (Math.min(x.bitLength(), y.bitLength()) / 2);
            if (m <= THRESHOLD) {
                return x.multiply(y);
            }

            BigInteger[] xs = split(x, m);
            BigInteger[] ys = split(y, m);

            KaratsubaTask z2task = new KaratsubaTask(xs[0], ys[0]);
            KaratsubaTask z0task = new KaratsubaTask(xs[1], ys[1]);
            KaratsubaTask z1task = new KaratsubaTask(add(xs), add(ys));

            z0task.fork();
            z2task.fork();
            BigInteger z0, z2;
            BigInteger z1 = z1task.invoke().subtract(
                    z2 = z2task.join()).subtract(z0 = z0task.join());

            return z2.shiftLeft(2 * m).add(z1.shiftLeft(m)).add(z0);
        }
    }
}
