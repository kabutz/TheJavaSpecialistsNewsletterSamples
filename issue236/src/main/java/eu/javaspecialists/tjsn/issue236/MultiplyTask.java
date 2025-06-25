package eu.javaspecialists.tjsn.issue236;

import java.math.*;
import java.util.concurrent.*;

class MultiplyTask extends RecursiveTask<BigInteger> {
    private final BigInteger b1, b2;

    public MultiplyTask(BigInteger b1, BigInteger b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    protected BigInteger compute() {
        return b1.multiply(b2);
    }
}
